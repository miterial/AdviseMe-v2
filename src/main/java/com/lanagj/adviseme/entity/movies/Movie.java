package com.lanagj.adviseme.entity.movies;

import com.lanagj.adviseme.entity.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Document("movies")
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movie extends Entity {

    @Getter
    final
    Integer tmdbId;
    final List<Genre> genres;
    /**
     * Localized title
     */
    final String title;
    final String overview;
    Date releaseDate;
    final Integer voteCount;
    final Float voteAverage;

    public Movie(@NonNull Integer tmdbId, List<Genre> genres, String title, @NonNull String overview, String releaseDate, Integer voteCount, Float voteAverage) {

        this.tmdbId = tmdbId;
        this.genres = genres;
        this.title = title;
        this.overview = overview;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            this.releaseDate = sdf.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Movie(Integer tmdbId, String overview) {
        this(tmdbId, null, null, overview, null, null, null);
    }
}
