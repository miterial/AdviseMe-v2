package com.lanagj.adviseme.converter;

import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.Genre;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TmdbToMovieConverter implements Converter<BaseMovie, com.lanagj.adviseme.entity.movie.Movie> {

    @Override
    public com.lanagj.adviseme.entity.movie.Movie convert(@NonNull BaseMovie source) {

        List<com.lanagj.adviseme.entity.movie.Genre> genres = new ArrayList<>();
        if(source.genres != null) {
            for (Genre genre : source.genres) {
                genres.add(new com.lanagj.adviseme.entity.movie.Genre(genre.id, genre.name));
            }
        }

        com.lanagj.adviseme.entity.movie.Movie movie = new com.lanagj.adviseme.entity.movie.Movie(
                source.id,
                genres,
                source.title,
                source.overview,
                source.release_date,
                source.vote_count,
                source.vote_average.floatValue());

        return movie;

    }
}
