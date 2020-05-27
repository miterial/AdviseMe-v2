package com.lanagj.adviseme.controller.movie;

import com.lanagj.adviseme.controller.user.EntityNotFoundException;
import com.lanagj.adviseme.entity.movie.Movie;
import com.lanagj.adviseme.entity.movie.MovieRepository;
import com.lanagj.adviseme.entity.movie_list.UserMovie;
import com.lanagj.adviseme.entity.user.User;
import com.lanagj.adviseme.entity.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class MovieService {

    UserRepository userRepository;
    MovieRepository movieRepository;

    public List<UserMovieDtoOut> getUserMovies(String userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User"));

        List<String> moviesIds = new ArrayList<>();
        for (UserMovie ratedMovie : user.getMovies()) {
            moviesIds.add(ratedMovie.getId());
        }

        Map<String, Movie> result = new HashMap<>();
        this.movieRepository.findAllById(moviesIds).forEach(m -> result.put(m.getId(), m));

        return this.convertToDto(user.getMovies(), result);
    }

    private List<UserMovieDtoOut> convertToDto(List<UserMovie> userMovies, Map<String, Movie> movieEntities) {

        List<UserMovieDtoOut> result = new ArrayList<>();
        for (UserMovie userMovie : userMovies) {
            Movie movie = movieEntities.get(userMovie.getId());
            if(movie == null) {
                throw new EntityNotFoundException("Movie");
            }

            LocalDate localDate = LocalDate.ofEpochDay(userMovie.getDate());
            result.add(new UserMovieDtoOut(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getOverview(),
                    userMovie.getRating(),
                    localDate));

        }
        return result;
    }
}
