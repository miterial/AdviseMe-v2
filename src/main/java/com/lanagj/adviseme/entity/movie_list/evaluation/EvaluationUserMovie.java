package com.lanagj.adviseme.entity.movie_list.evaluation;

import com.lanagj.adviseme.configuration.AlgorithmType;
import com.lanagj.adviseme.entity.movie_list.AbstractUserMovie;
import com.lanagj.adviseme.entity.movie_list.UserMovieStatus;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@ToString(callSuper = true)
public class EvaluationUserMovie extends AbstractUserMovie {

    private AlgorithmType algorithmType;
    private UserMovieStatus status;

    public EvaluationUserMovie(Integer userId, Integer movieId, UserMovieStatus status, AlgorithmType algorithmType) {

        super(userId, movieId);
        this.algorithmType = algorithmType;
        this.status = status;
    }

    @Override
    public Integer getUserId() {

        return (Integer)super.getUserId();
    }

    @Override
    public Integer getMovieId() {

        return (Integer)super.getMovieId();
    }
}
