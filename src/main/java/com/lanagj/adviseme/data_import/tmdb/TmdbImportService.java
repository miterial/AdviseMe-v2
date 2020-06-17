package com.lanagj.adviseme.data_import.tmdb;

import com.lanagj.adviseme.converter.GeneralConverterService;
import com.lanagj.adviseme.entity.movie.Movie;
import com.lanagj.adviseme.entity.movie.MovieRepository;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.DiscoverFilter;
import com.uwetrottmann.tmdb2.entities.GenreResults;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.enumerations.SortBy;
import com.uwetrottmann.tmdb2.services.DiscoverService;
import com.uwetrottmann.tmdb2.services.GenresService;
import com.uwetrottmann.tmdb2.services.MoviesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@AllArgsConstructor
public class TmdbImportService {

    DiscoverService discoverService;
    GenresService genresService;
    MoviesService moviesService;

    GeneralConverterService movieConverter;
    MovieRepository movieRepository;

    /**
     * Initial movie import
     *
     * @param countLimit
     * @return
     */
    public Set<Integer> importMovies(int pageStart, int pageEnd, int countLimit) {

        List<BaseMovie> moviesBaseInfo = new ArrayList<>();

        for (int year = 1996; year <= 2019; year++) {
            moviesBaseInfo.addAll(this.getFromPage(pageStart, pageEnd, year));
        }

        // filter out movies without overview or that don't have translation
        Pattern pattern = Pattern.compile("[^a-zA-Z]");
        moviesBaseInfo.removeIf(
                m -> m.overview == null ||
                        m.overview.isEmpty() ||
                        !pattern.matcher(m.overview).find());

        //this.fillWithGenres(moviesBaseInfo);

        List<com.lanagj.adviseme.entity.movie.Movie> movieEntities = this.movieConverter.convertList(moviesBaseInfo, BaseMovie.class, com.lanagj.adviseme.entity.movie.Movie.class);

        // save limited amount of movies
        int max = Math.min(countLimit, movieEntities.size());
        System.out.println("importing movies: " + max);
        List<Movie> movies = this.movieRepository.saveAll(movieEntities.subList(0, max));
        return movies.stream().map(Movie::getTmdbId).collect(Collectors.toSet());
    }

    private List<BaseMovie> fillWithGenres(List<BaseMovie> moviesBaseInfo) {

        try {
            Response<GenreResults> response = this.genresService.movie("ru").execute();
            GenreResults genres = response.body();
            for (BaseMovie baseMovie : moviesBaseInfo) {
                baseMovie.genres = genres.genres.stream()
                        .filter(g -> baseMovie.genre_ids.contains(g.id))
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            log.error("Error retrieving genres. Cause: {}", e.getMessage());
        }

        return moviesBaseInfo;
    }

    /**
     * Get movies filtered by year and sorted by release date
     *
     * @param pageStart page start
     * @param year      movie release year
     * @return list of movies from pages
     */
    private List<BaseMovie> getFromPage(int pageStart, int pageEnd, int year) {

        List<BaseMovie> pageResultsList = new ArrayList<>();

        List<CompletableFuture<MovieResultsPage>> listOfFutures = IntStream.rangeClosed(pageStart, pageEnd)
                .mapToObj(i -> CompletableFuture.supplyAsync(
                        () -> {
                            MovieResultsPage pageResults;
                            try {
                                Response<MovieResultsPage> response = discoverService.discoverMovie("ru", null, SortBy.RELEASE_DATE_DESC, null, null, null, null, null, i, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, year, null, null, null, new DiscoverFilter(1), null, null).execute();

                                pageResults = response.body();
                                return pageResults;

                            } catch (IOException | NullPointerException e) {
                                log.error("Error retrieving movies in year {}. Cause: {}", year, e.getMessage());
                            }
                            // todo: this is unsafe
                            return new MovieResultsPage();
                        })
                ).collect(Collectors.toList());

        List<BaseMovie> collect = listOfFutures.stream().map(CompletableFuture::join).map(mrp -> mrp.results).flatMap(List::stream).collect(Collectors.toList());

        pageResultsList.addAll(collect);

        return pageResultsList;
    }

    public List<Movie> importMovies(Set<Integer> movieIds) {

        List<BaseMovie> movies = new ArrayList<>();
        for (Integer movieId : movieIds) {
            try {
                com.uwetrottmann.tmdb2.entities.Movie movie = this.moviesService.summary(movieId, "ru").execute().body();

                if(movie != null) {
                    // filter out movies without overview or that don't have translation
                    if(!movie.overview.isEmpty()) {
                        Pattern pattern = Pattern.compile("[^a-zA-Z]");
                        if (pattern.matcher(movie.overview).find()) {
                            movies.add(movie);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<com.lanagj.adviseme.entity.movie.Movie> movieEntities = this.movieConverter.convertList(movies, BaseMovie.class, com.lanagj.adviseme.entity.movie.Movie.class);
        return movieEntities;
    }
}
