package com.lanagj.adviseme.controller.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserMovieDtoIn {

    String user_id;
    String movie_id;
    Double rating;
}
