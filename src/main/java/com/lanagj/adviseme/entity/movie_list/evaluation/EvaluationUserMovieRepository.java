package com.lanagj.adviseme.entity.movie_list.evaluation;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EvaluationUserMovieRepository extends MongoRepository<EvaluationUserMovie, String> {

}
