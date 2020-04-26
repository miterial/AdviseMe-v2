package com.lanagj.adviseme.entity.movie;

import com.lanagj.adviseme.entity.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Movie extends Entity {

    @Getter
    Integer tmdbId;
    List<Genre> genres;
    /**
     * Localized title
     */
    String title;
    String overview;
    Date releaseDate;
    Integer voteCount;
    Float voteAverage;

    public Movie(@NonNull Integer tmdbId, List<Genre> genres, @NonNull String title, @NonNull String overview, @NonNull Date releaseDate, @NonNull Integer voteCount, @NonNull Float voteAverage) {

        this.tmdbId = tmdbId;
        this.genres = genres;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
    }
}
