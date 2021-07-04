package com.lanagj.adviseme.entity.user;

import com.lanagj.adviseme.entity.Entity;
import com.lanagj.adviseme.entity.movie_list.UserMovie;
import com.lanagj.adviseme.entity.movies.Movie;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Document("users")
public class User extends Entity {

    @Indexed(unique = true)
    String login;

    String password;    // encrypted password

    Role role;

    public User(String login, String password, Role role) {

        this.login = login;
        this.password = password;
        this.role = role;
    }

    public List<UserMovie> getMovies() {

        // stub to support deprecated code
        return null;
    }
}
