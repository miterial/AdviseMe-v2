package com.lanagj.adviseme.entity.movies;

import java.util.List;
import java.util.Set;

public interface MovieRepository {

    List<Movie> findAllByTmdbIdIn(Set<Integer> tmdbIds);

    List<Movie> getTopRated();

    void save(List<Movie> movies);

    List<Movie> findAllById(Set<String> movieIds);

    List<Movie> findAll();
}
