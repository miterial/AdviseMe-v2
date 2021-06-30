package com.lanagj.adviseme.converter;

import com.lanagj.adviseme.entity.movies.Movie;
import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.MovieDb;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TmdbToMovieConverter implements Converter<MovieDb, Movie> {

    @Override
    public Movie convert(@NonNull MovieDb source) {

        List<com.lanagj.adviseme.entity.movies.Genre> genres = new ArrayList<>();
        if (source.getGenres() != null) {
            for (Genre genre : source.getGenres()) {
                genres.add(new com.lanagj.adviseme.entity.movies.Genre(genre.getId(), genre.getName()));
            }
        }

        Movie movie = new Movie(
                source.getId(),
                genres,
                source.getTitle(),
                source.getOverview(),
                source.getReleaseDate(),
                source.getVoteCount(),
                source.getVoteAverage());

        return movie;
    }
}
