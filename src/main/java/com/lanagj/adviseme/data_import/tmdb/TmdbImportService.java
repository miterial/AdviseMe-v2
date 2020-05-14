package com.lanagj.adviseme.data_import.tmdb;

import com.lanagj.adviseme.converter.GeneralConverterService;
import com.lanagj.adviseme.entity.movie.MovieRepository;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.DiscoverFilter;
import com.uwetrottmann.tmdb2.entities.GenreResults;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.enumerations.SortBy;
import com.uwetrottmann.tmdb2.services.DiscoverService;
import com.uwetrottmann.tmdb2.services.GenresService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TmdbImportService {

    DiscoverService discoverService;
    GenresService genresService;
    GeneralConverterService movieConverter;
    MovieRepository movieRepository;

    /**
     * Initial movie import
     */
    public void importMovies(int pageLimit, int yearMin, int yearMax) {

        List<BaseMovie> moviesBaseInfo = new ArrayList<>();

        for (int year = yearMin; year <= yearMax; year++) {
            moviesBaseInfo.addAll(this.getFromPage(pageLimit, year));
        }

        // filter out movies without overview or that don't have translation
        Pattern pattern = Pattern.compile("[^a-zA-Z]");
        moviesBaseInfo.removeIf(
                        m -> m.overview == null ||
                        m.overview.isEmpty() ||
                        !pattern.matcher(m.overview).find());

        this.fillWithGenres(moviesBaseInfo);

        List<com.lanagj.adviseme.entity.movie.Movie> movieEntities = this.movieConverter.convertList(moviesBaseInfo, BaseMovie.class, com.lanagj.adviseme.entity.movie.Movie.class);

        for (int i = 0; i < movieEntities.size(); i++) {
            System.out.println((i+1));
            System.out.println(movieEntities.get(i).getOverview() + "\n");
        }

        this.movieRepository.saveAll(movieEntities);
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
     * @param page page limit
     * @param year movie release year
     * @return list of movies from pages
     */
    private List<BaseMovie> getFromPage(int page, int year) {

        List<BaseMovie> pageResultsList = new ArrayList<>();
        MovieResultsPage pageResults;
        for (int i = 1; i <= page; i++) {
            try {
                Response<MovieResultsPage> response = discoverService.discoverMovie("ru", null, SortBy.RELEASE_DATE_DESC, null, null, null, null, null, i, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, year, null, null, null, new DiscoverFilter(1), null, null).execute();

                pageResults = response.body();
                pageResultsList.addAll(pageResults.results);

            } catch (IOException | NullPointerException e) {
                log.error("Error retrieving movies on page {} in year {}. Cause: {}", page, year, e.getMessage());
            }
        }

        return pageResultsList;
    }

}
