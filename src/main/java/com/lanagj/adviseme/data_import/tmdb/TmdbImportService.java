package com.lanagj.adviseme.data_import.tmdb;

import com.lanagj.adviseme.converter.GeneralConverterService;
import com.lanagj.adviseme.data_import.tmdb.discover.SortBy;
import com.lanagj.adviseme.entity.movies.Movie;
import com.lanagj.adviseme.entity.movies.MovieRepository;
import info.movito.themoviedbapi.TmdbDiscover;
import info.movito.themoviedbapi.TmdbGenre;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.Discover;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
public class TmdbImportService {

    TmdbDiscover discoverService;
    TmdbGenre genresService;
    TmdbMovies moviesService;

    String language = "ru";

    public TmdbImportService(TmdbDiscover discoverService, TmdbGenre genresService, TmdbMovies moviesService) {

        this.discoverService = discoverService;
        this.genresService = genresService;
        this.moviesService = moviesService;
    }

    public MovieResultsPage getFromPageWithSort(int pageStart, SortBy sortBy) {

        Discover discover = new Discover()
                .page(pageStart)
                .language(language)
                .sortBy(sortBy.getFilter());

        return this.discoverService.getDiscover(discover);
    }

    public MovieResultsPage getLatest(int page) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        Discover discover = new Discover()
                .page(page)
                .language(language)
                .sortBy(SortBy.RELEASE_DATE_DESC.getFilter())
                .releaseDateGte(year + "-" + month + "-" + day);

        return this.discoverService.getDiscover(discover);
    }

    public List<MovieDb> importTopRated() {

        List<MovieDb> resultList = new ArrayList<>();

        MovieResultsPage topRatedMoviesFirstPage = this.moviesService.getTopRatedMovies(language, 1);
        resultList.addAll(topRatedMoviesFirstPage.getResults());
        int totalPages = topRatedMoviesFirstPage.getTotalPages();

        for (int i = 2; i <= totalPages; i++) {
            MovieResultsPage topRatedMovies = this.moviesService.getTopRatedMovies(language, i);
            resultList.addAll(topRatedMovies.getResults());
        }

        return resultList;
    }
}
