package com.lanagj.adviseme.entity.movie_list;

import com.lanagj.adviseme.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
abstract class AbstractUserMovie extends Entity {

    /** ID of the movie that was marked by user as seen */
    String movieId;
    /** Defines whether user has already seen this movie */
    UserMovieStatus type;

}
