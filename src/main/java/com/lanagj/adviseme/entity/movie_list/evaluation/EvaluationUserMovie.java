package com.lanagj.adviseme.entity.movie_list.evaluation;

import com.lanagj.adviseme.entity.movie_list.AbstractUserMovie;
import com.lanagj.adviseme.entity.movie_list.UserMovieStatus;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@ToString(callSuper = true)
public class EvaluationUserMovie extends AbstractUserMovie {

    public EvaluationUserMovie(Integer userId, Integer movieId, UserMovieStatus status) {

        super(userId, movieId, status);
    }

    @Override
    public Integer getUserId() {

        return (Integer) super.getUserId();
    }

    @Override
    public Integer getMovieId() {

        return (Integer) super.getMovieId();
    }
}
