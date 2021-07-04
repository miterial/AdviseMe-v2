package com.lanagj.adviseme.entity.user_movies;

import com.lanagj.adviseme.entity.movie_list.AbstractUserMovie;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user_movies")
public class UserMovie extends AbstractUserMovie {

    UserMovieStatus status;

    public UserMovie(Object userId, Object movieId) {

        super(userId, movieId);
    }
}
