package com.lanagj.adviseme.entity.movie_list.evaluation;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Set;

public interface TestUserMovieRepository extends MongoRepository<TestUserMovie, String> {

    void deleteByMovieIdIn(Set<Integer> testUserMovies);
}
