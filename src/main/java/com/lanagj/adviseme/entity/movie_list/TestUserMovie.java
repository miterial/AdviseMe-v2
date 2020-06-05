package com.lanagj.adviseme.entity.movie_list;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@ToString(callSuper = true)
public class TestUserMovie extends AbstractUserMovie {

    Integer userId;

    public TestUserMovie(Integer userId, String movieId, UserMovieStatus type) {

        super(movieId, type);
        this.userId = userId;
    }
}
