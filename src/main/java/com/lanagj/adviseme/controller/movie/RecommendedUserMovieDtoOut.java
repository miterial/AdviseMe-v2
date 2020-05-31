package com.lanagj.adviseme.controller.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class RecommendedUserMovieDtoOut extends UserMovieDtoOut {

    Integer like_probability;

    public RecommendedUserMovieDtoOut(String movie_id, String movie_title, String movie_overview, String user_rating, Double avg_rating, LocalDate user_rating_date, Integer like_probability) {

        super(movie_id, movie_title, movie_overview, user_rating, avg_rating, user_rating_date);
        this.like_probability = like_probability;
    }
}
