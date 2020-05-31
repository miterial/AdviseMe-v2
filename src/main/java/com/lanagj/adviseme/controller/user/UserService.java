package com.lanagj.adviseme.controller.user;

import com.lanagj.adviseme.controller.exception.EntityNotFoundException;
import com.lanagj.adviseme.entity.movie_list.UserMovie;
import com.lanagj.adviseme.entity.movie_list.UserMovieRepository;
import com.lanagj.adviseme.entity.user.Role;
import com.lanagj.adviseme.entity.user.User;
import com.lanagj.adviseme.entity.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Instant;

@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;
    UserMovieRepository userMovieRepository;

    public User getUser(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User"));
    }

    public void addMovieRating(String userId, String movieId, Double rating) {

        UserMovie userMovie = new UserMovie(movieId, rating, Instant.now().toEpochMilli());
        this.userMovieRepository.save(userMovie);

        User user = this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User"));
        user.getMovies().add(userMovie);
        this.userRepository.save(user);
    }
}
