package com.lanagj.adviseme.controller.movie;

import com.lanagj.adviseme.controller.user.UserService;
import com.lanagj.adviseme.controller.user.dto.UserMovieDtoIn;
import com.lanagj.adviseme.entity.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class MovieController {

    UserService userService;
    MovieService movieService;

    @GetMapping("/movies/{userId}")
    public ModelAndView getUserProfile(ModelAndView model, @PathVariable String userId) {

        User user = this.userService.getSimpleUser();
        model.addObject("user", user);

        List<UserMovieDtoOut> movies = this.movieService.getUserMovies(userId);
        model.addObject("movies", movies);

        model.setViewName("user_profile");

        return model;
    }
}
