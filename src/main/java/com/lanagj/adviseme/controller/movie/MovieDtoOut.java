package com.lanagj.adviseme.controller.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieDtoOut {

    String movie_id;
    String movie_title;
    String movie_overview;
    Double avg_rating;

    //todo: 2-3 new fields with recommender ratings (lsa, mlsa, esa)
}
