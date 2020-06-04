package com.lanagj.adviseme.controller.user;

import com.lanagj.adviseme.configuration.security.UserPrincipal;
import com.lanagj.adviseme.controller.movie.MovieService;
import com.lanagj.adviseme.controller.movie.UserMovieDtoOut;
import com.lanagj.adviseme.controller.user.dto.UserMovieDtoIn;
import com.lanagj.adviseme.entity.movie_list.UserMovieType;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    UserService userService;
    MovieService movieService;

    @GetMapping
    public ModelAndView getUserProfile(ModelAndView model, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        model.addObject("username", userPrincipal.getUsername());
        model.setViewName("user_profile");
        return model;
    }

    @GetMapping("/liked")
    public List<UserMovieDtoOut> getLikedMovies(@AuthenticationPrincipal UserPrincipal userPrincipal) {

        List<UserMovieDtoOut> movies = this.userService.getMovies(UserMovieType.LIKED, userPrincipal.getId());
        return movies;
    }

    @GetMapping("/disliked")
    public List<UserMovieDtoOut> getDislikedMovies(@AuthenticationPrincipal UserPrincipal userPrincipal) {

        List<UserMovieDtoOut> movies = this.userService.getMovies(UserMovieType.DISLIKED, userPrincipal.getId());
        return movies;
    }

    @GetMapping("/recommended")
    public List<UserMovieDtoOut> getRecommendedMovies(@AuthenticationPrincipal UserPrincipal userPrincipal) {

        List<UserMovieDtoOut> movies = this.userService.getMovies(UserMovieType.RECOMMENDED, userPrincipal.getId());
        return movies;
    }

    @PostMapping
    public void addMovieRating(@RequestBody UserMovieDtoIn dto) {
        this.userService.addMovieRating(dto.getUser_id(), dto.getMovie_id(), dto.getRating());
    }
}
