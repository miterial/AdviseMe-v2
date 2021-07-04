package com.lanagj.adviseme.entity.movie_list;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@ToString(callSuper = true)
@Deprecated
public class UserMovie extends AbstractUserMovie {

    /** User rating for this movie */
    Double rating;
    /** Date when the movie was marked as seen */
    Long date;
    /** Defines whether user has already seen this movie */
    UserMovieStatus status;

    public UserMovie(String userId, String movieId, UserMovieStatus status, Double rating, Long date) {

        super(userId, movieId);
        this.rating = rating;
        this.date = date;
        this.status = status;
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
