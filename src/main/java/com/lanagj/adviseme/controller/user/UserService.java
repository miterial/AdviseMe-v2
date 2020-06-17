package com.lanagj.adviseme.controller.user;

import com.lanagj.adviseme.configuration.ApplicationSettings;
import com.lanagj.adviseme.controller.exception.EntityNotFoundException;
import com.lanagj.adviseme.controller.movie.MovieService;
import com.lanagj.adviseme.controller.movie.UserMovieDtoOut;
import com.lanagj.adviseme.entity.movie.Movie;
import com.lanagj.adviseme.entity.movie_list.UserMovieStatus;
import com.lanagj.adviseme.entity.movie_list.UserMovie;
import com.lanagj.adviseme.entity.movie_list.UserMovieRepository;
import com.lanagj.adviseme.entity.movie_list.evaluation.EvaluationUserMovie;
import com.lanagj.adviseme.entity.movie_list.evaluation.EvaluationUserMovieRepository;
import com.lanagj.adviseme.entity.user.User;
import com.lanagj.adviseme.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Value("${rating.limit}")
    Double ratingLimit;

    UserRepository userRepository;
    UserMovieRepository userMovieRepository;
    MovieService movieService;

    EvaluationUserMovieRepository evaluationUserMovieRepository;

    public UserService(UserRepository userRepository, UserMovieRepository userMovieRepository, MovieService movieService, EvaluationUserMovieRepository repository) {

        this.userRepository = userRepository;
        this.userMovieRepository = userMovieRepository;
        this.movieService = movieService;
        this.evaluationUserMovieRepository = repository;
    }

    public User getUser(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User"));
    }

    void addMovieRating(String userId, String movieId, Double rating) {

        UserMovieStatus type;
        if(rating > ratingLimit) {
            type = UserMovieStatus.LIKED;
        } else {
            type = UserMovieStatus.DISLIKED;
        }

        UserMovie userMovie = new UserMovie(userId, movieId, type, rating, Instant.now().toEpochMilli());
        this.userMovieRepository.save(userMovie);

        User user = this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User"));
        user.getMovies().add(userMovie);
        this.userRepository.save(user);
    }

    List<UserMovieDtoOut> getMovies(UserMovieStatus userMovieStatus, String userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User"));

        Map<String, UserMovie> userMovies = user.getMovies().stream().filter(m -> m.getStatus() == userMovieStatus).collect(Collectors.toMap(UserMovie::getMovieId, Function.identity()));

        List<Movie> movies = this.movieService.getMovies(userMovies.keySet());

        List<UserMovieDtoOut> result = new ArrayList<>();
        NumberFormat nf = NumberFormat.getInstance();
        for (Movie movie : movies) {
            Double userRating = userMovies.get(movie.getId()).getRating();
            Long userRatingDateLong = userMovies.get(movie.getId()).getDate();
            Date userRatingDate = new Date(userRatingDateLong);
            nf.setMaximumFractionDigits(1);
            String formattedAvgRating = nf.format(movie.getVoteAverage().doubleValue() / 2);
            result.add(new UserMovieDtoOut(movie.getId(), movie.getTitle(), movie.getOverview(), formattedAvgRating, userRating, userRatingDate));
        }

        return result;
    }

    List<UserMovieDtoOut> getRecommendedMovies(UserMovieStatus userMovieStatus, String userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User"));

        Set<Integer> recommendedIds = this.evaluationUserMovieRepository.findAll().stream().filter(eum -> eum.getStatus() == UserMovieStatus.RECOMMENDED && eum.getAlgorithmType() == ApplicationSettings.getInstance().getType()).map(EvaluationUserMovie::getMovieId).collect(Collectors.toSet());

        List<Movie> movies = this.movieService.getMoviesByTmdbIds(recommendedIds);

        Collections.shuffle(movies);

        movies = movies.subList(0, 56);

        List<UserMovieDtoOut> result = new ArrayList<>();
        NumberFormat nf = NumberFormat.getInstance();
        for (Movie movie : movies) {
            nf.setMaximumFractionDigits(1);
            String formattedAvgRating = nf.format(movie.getVoteAverage().doubleValue() / 2);
            result.add(new UserMovieDtoOut(movie.getId(), movie.getTitle(), movie.getOverview(), formattedAvgRating, movie.getReleaseDate()));
        }

        return result;
    }
}
