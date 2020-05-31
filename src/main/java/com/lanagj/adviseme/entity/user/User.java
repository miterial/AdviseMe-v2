package com.lanagj.adviseme.entity.user;

import com.lanagj.adviseme.entity.Entity;
import com.lanagj.adviseme.entity.movie_list.UserMovie;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document
public class User extends Entity {

    @Indexed(unique = true)
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

    public User(String login, String password, Role role) {

        this.login = login;
        this.password = password;
        this.role = role;
    }
}
