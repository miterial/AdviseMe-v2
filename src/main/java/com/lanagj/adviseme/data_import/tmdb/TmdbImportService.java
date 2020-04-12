package com.lanagj.adviseme.data_import.tmdb;

import com.lanagj.adviseme.converter.GeneralConverterService;
import com.lanagj.adviseme.entity.movie.MovieRepository;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.DiscoverFilter;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.enumerations.SortBy;
import com.uwetrottmann.tmdb2.services.DiscoverService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TmdbImportService {

    DiscoverService discoverService;
    GeneralConverterService movieConverter;
    MovieRepository movieRepository;

    /**
     * Initial movie import
     * todo: customize to import frequently
     */
    public void importMovies() {

        int pageLimit = 1000;
        int yearMin = 2000;
        int yearMax = 2019;

        List<BaseMovie> moviesBaseInfo = new ArrayList<>();

        for (int year = yearMin; year < yearMax; year++) {
            moviesBaseInfo.addAll(this.getFromPage(pageLimit, year));
        }

        //List<Movie> moviesWithGenres = this.addGenres(moviesBaseInfo);

        List<com.lanagj.adviseme.entity.movie.Movie> movieEntities = this.movieConverter.convertList(moviesBaseInfo, BaseMovie.class, com.lanagj.adviseme.entity.movie.Movie.class);

        this.movieRepository.saveAll(movieEntities);
    }

    private List<Movie> addGenres(List<BaseMovie> moviesBaseInfo) {

        return null;
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
                pageResults = discoverService.discoverMovie("ru", null, SortBy.RELEASE_DATE_DESC, null, null, null, null, null, i, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, year, null, null, null, new DiscoverFilter(1), null, null).execute().body();

                pageResultsList.addAll(pageResults.results);

            } catch (IOException | NullPointerException e) {
                log.error("Error retrieving movies on page {} in year {}. Cause: {}", page, year, e.getMessage());
            }
        }

        return pageResultsList;
    }

}
