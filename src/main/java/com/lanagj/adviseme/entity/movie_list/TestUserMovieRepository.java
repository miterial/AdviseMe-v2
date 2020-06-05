package com.lanagj.adviseme.entity.movie_list;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestUserMovieRepository extends MongoRepository<TestUserMovie, String> {

}
