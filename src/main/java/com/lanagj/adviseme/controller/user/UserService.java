package com.lanagj.adviseme.controller.user;

import com.lanagj.adviseme.entity.movie_list.UserMovie;
import com.lanagj.adviseme.entity.movie_list.UserMovieRepository;
import com.lanagj.adviseme.entity.user.Role;
import com.lanagj.adviseme.entity.user.User;
import com.lanagj.adviseme.entity.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;
    UserMovieRepository userMovieRepository;

    public User getSimpleUser() {
        return this.userRepository.findByRole(Role.SIMPLE).get(0);
    }

    public void addMovieRating(String userId, Integer movieId, Double rating) {

        UserMovie userMovie = new UserMovie(movieId, rating, Instant.now().toEpochMilli());
        this.userMovieRepository.save(userMovie);

        User user = this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User"));
        user.getMovies().add(userMovie);
        this.userRepository.save(user);
    }
}
