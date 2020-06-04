package com.lanagj.adviseme.controller.user;

import com.lanagj.adviseme.controller.exception.EntityNotFoundException;
import com.lanagj.adviseme.controller.movie.MovieService;
import com.lanagj.adviseme.controller.movie.UserMovieDtoOut;
import com.lanagj.adviseme.entity.movie.Movie;
import com.lanagj.adviseme.entity.movie_list.UserMovieType;
import com.lanagj.adviseme.entity.movie_list.UserMovie;
import com.lanagj.adviseme.entity.movie_list.UserMovieRepository;
import com.lanagj.adviseme.entity.user.User;
import com.lanagj.adviseme.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Value("${rating.limit}")
    Double ratingLimit;

    UserRepository userRepository;
    UserMovieRepository userMovieRepository;
    MovieService movieService;

    public UserService(UserRepository userRepository, UserMovieRepository userMovieRepository, MovieService movieService) {

        this.userRepository = userRepository;
        this.userMovieRepository = userMovieRepository;
        this.movieService = movieService;
    }

    public User getUser(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User"));
    }

    void addMovieRating(String userId, String movieId, Double rating) {

        UserMovieType type;
        if(rating > ratingLimit) {
            type = UserMovieType.LIKED;
        } else {
            type = UserMovieType.DISLIKED;
        }

        UserMovie userMovie = new UserMovie(movieId, rating, Instant.now().toEpochMilli(), type);
        this.userMovieRepository.save(userMovie);

        User user = this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User"));
        user.getMovies().add(userMovie);
        this.userRepository.save(user);
    }

    List<UserMovieDtoOut> getMovies(UserMovieType userMovieType, String userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User"));

        Map<String, UserMovie> userMovies = user.getMovies().stream().filter(m -> m.getType() == userMovieType).collect(Collectors.toMap(UserMovie::getMovieId, Function.identity()));

        List<Movie> movies = this.movieService.getMovies(userMovies.keySet());

        List<UserMovieDtoOut> result = new ArrayList<>();
        NumberFormat nf = NumberFormat.getInstance();
        for (Movie movie : movies) {
            Double userRating = userMovies.get(movie.getId()).getRating();
            Long userRatingDateLong = userMovies.get(movie.getId()).getDate();
            LocalDate userRatingDate = Instant.ofEpochMilli(userRatingDateLong).atZone(ZoneId.of("UTC")).toLocalDate();
            nf.setMaximumFractionDigits(1);
            String formattedAvgRating = nf.format(movie.getVoteAverage().doubleValue());
            result.add(new UserMovieDtoOut(movie.getId(), movie.getTitle(), movie.getOverview(), formattedAvgRating, userRating, userRatingDate));
        }

        return result;
    }
}
