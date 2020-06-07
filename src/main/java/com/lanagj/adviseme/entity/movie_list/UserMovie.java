package com.lanagj.adviseme.entity.movie_list;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@ToString(callSuper = true)
public class UserMovie extends AbstractUserMovie {

    /** User rating for this movie */
    Double rating;
    /** Date when the movie was marked as seen */
    Long date;

    public UserMovie(String userId, String movieId, UserMovieStatus type, Double rating, Long date) {

        super(userId, movieId, type);
        this.rating = rating;
        this.date = date;
    }

    @Override
    public String getMovieId() {

        return (String)super.getMovieId();
    }

    @Override
    public String getUserId() {

        return (String)super.getUserId();
    }
}
