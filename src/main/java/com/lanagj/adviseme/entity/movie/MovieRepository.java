package com.lanagj.adviseme.entity.movie;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, Integer> {

}
