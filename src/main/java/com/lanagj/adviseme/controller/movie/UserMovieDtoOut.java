package com.lanagj.adviseme.controller.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserMovieDtoOut {

    String movie_id;
    String movie_title;
    String movie_overview;

    Double user_rating;
    LocalDate user_rating_date;

    //todo: 2-3 new fields with recommender ratings (lsa, mlsa, esa)
}
