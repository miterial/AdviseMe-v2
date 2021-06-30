package com.lanagj.adviseme.controller.providers;

import com.lanagj.adviseme.converter.GeneralConverterService;
import com.lanagj.adviseme.data_import.tmdb.TmdbImportService;
import com.lanagj.adviseme.entity.movies.Movie;
import com.lanagj.adviseme.entity.movies.MovieRepository;
import info.movito.themoviedbapi.model.MovieDb;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class MoviesProvider {

    TmdbImportService tmdbImportService;
    MovieRepository movieRepository;
    GeneralConverterService movieConverter;

    public List<Movie> getTopRated() {

        List<Movie> topRated = this.movieRepository.getTopRated();

        if (topRated.isEmpty()) {
            List<MovieDb> importedMovies = this.tmdbImportService.importTopRated();
            List<Movie> movies = this.movieConverter.convertList(importedMovies, MovieDb.class, Movie.class);
            this.movieRepository.save(movies);
            return movies;
        }

        return topRated;
    }
}
