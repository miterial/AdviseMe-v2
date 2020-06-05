package com.lanagj.adviseme.entity.movie_list;

/**
 * Defines how the movie corresponds to user
 */
public enum UserMovieStatus {
    LIKED,
    DISLIKED,
    RECOMMENDED,        // this status can change to NOT_RECOMMENDED
    NOT_RECOMMENDED     // this status can change to RECOMMENDED
}
