package com.lanagj.adviseme.entity.movie_list;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@ToString(callSuper = true)
public class EvaluationUserMovie extends TestUserMovie {

    public EvaluationUserMovie(Integer userId, String movieId, UserMovieStatus type) {

        super(userId, movieId, type);
    }
}
