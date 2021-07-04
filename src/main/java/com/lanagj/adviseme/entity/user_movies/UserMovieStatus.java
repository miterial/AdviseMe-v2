package com.lanagj.adviseme.entity.user_movies;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class UserMovieStatus {

    /** How user rated this movie */
    Double rating;

    /** When user set the rating */
    Long dateAdded;

    /** Text review that user wrote for this movie */
    String review;

    /** Calculated based on user rating or set directly to 'IGNORED' */
    //UserMovieImpression impression;

    public UserMovieStatus(@NonNull Double rating, @NonNull Long dateAdded, String review) {

        this.rating = rating;
        this.dateAdded = dateAdded;
        this.review = review;
    }
}
