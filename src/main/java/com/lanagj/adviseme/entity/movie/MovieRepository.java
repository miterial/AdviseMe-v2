package com.lanagj.adviseme.entity.movie;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface MovieRepository extends MongoRepository<Movie, String> {

    List<Movie> findAllByTmdbIdIn(Set<Integer> tmdbIds);
}
