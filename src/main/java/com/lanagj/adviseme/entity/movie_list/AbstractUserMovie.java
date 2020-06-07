package com.lanagj.adviseme.entity.movie_list;

import com.lanagj.adviseme.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public abstract class AbstractUserMovie extends Entity {

    /** User ID that can be String for real users and Integer for evaluation users */
    Object userId;
    /** ID of the movie that was marked by user as seen; can be String for real users and Integer for evaluation users */
    Object movieId;
    /** Defines whether user has already seen this movie */
    UserMovieStatus status;

}
