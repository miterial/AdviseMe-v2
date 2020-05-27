package com.lanagj.adviseme.entity.user;

import com.lanagj.adviseme.entity.Entity;
import com.lanagj.adviseme.entity.movie_list.UserMovie;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class User extends Entity {

    String login;
    String password;
    Role role;

    @DBRef
    List<UserMovie> movies;

    public List<UserMovie> getMovies() {
        if(this.movies == null) {
            this.movies = new ArrayList<>();
        }
        return this.movies;
    }
}
