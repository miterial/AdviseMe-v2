package com.lanagj.adviseme.entity.movie_list;

import com.lanagj.adviseme.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@Data
public class UserMovie extends Entity {

    /** ID of the movie that was marked by user as seen */
    String movieId;
    /** User rating for this movie */
    Double rating;
    /** Date when the movie was marked as seen */
    Long date;
    /** Defines whether user has already seen this movie */
    UserMovieType type;
}
