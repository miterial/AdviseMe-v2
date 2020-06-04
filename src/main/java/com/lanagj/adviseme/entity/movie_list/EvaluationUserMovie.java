package com.lanagj.adviseme.entity.movie_list;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class EvaluationUserMovie extends UserMovie {

    Integer userId;

    public EvaluationUserMovie(Integer userId, String movieId, Double rating, Long date) {

        super(movieId, rating, date, null);
        this.userId = userId;
        super.type = rating > 3.0 ? UserMovieType.LIKED : UserMovieType.DISLIKED;
    }
}
