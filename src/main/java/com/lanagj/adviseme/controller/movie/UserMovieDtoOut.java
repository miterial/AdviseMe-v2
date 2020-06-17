package com.lanagj.adviseme.controller.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@Data
public class UserMovieDtoOut extends  MovieDtoOut {

    Double user_rating;
    Date user_rating_date;

    //todo: 2-3 new fields with recommender ratings (lsa, mlsa, esa)

    public UserMovieDtoOut(String movie_id, String movie_title, String movie_overview, String avg_rating, Double user_rating, Date user_rating_date) {

        super(movie_id, movie_title, movie_overview, avg_rating);
        this.user_rating = user_rating;
        this.user_rating_date = user_rating_date;
    }

    public UserMovieDtoOut(String id, String title, String overview, String formattedAvgRating, Date user_rating_date) {

        super(id, title, overview, formattedAvgRating);
        this.user_rating_date = new Date(user_rating_date.toInstant().toEpochMilli());

    }
}
