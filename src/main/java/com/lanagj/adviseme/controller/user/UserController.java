package com.lanagj.adviseme.controller.user;

import com.lanagj.adviseme.configuration.security.UserPrincipal;
import com.lanagj.adviseme.controller.movie.MovieService;
import com.lanagj.adviseme.controller.movie.UserMovieDtoOut;
import com.lanagj.adviseme.controller.user.dto.UserMovieDtoIn;
import com.lanagj.adviseme.entity.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    UserService userService;
    MovieService movieService;

    @GetMapping
    public ModelAndView getUserProfile(ModelAndView model, @AuthenticationPrincipal UserPrincipal userPrincipal) {

        User user = this.userService.getUser(userPrincipal.getId());
        model.addObject("user", user);

        List<UserMovieDtoOut> movies = this.movieService.getUserMovies(user.getId());
        model.addObject("movies", movies);

        model.setViewName("user_profile");

        return model;
    }

    @PostMapping
    public void addMovieRating(@RequestBody UserMovieDtoIn dto) {
        this.userService.addMovieRating(dto.getUser_id(), dto.getMovie_id(), dto.getRating());
    }
}
