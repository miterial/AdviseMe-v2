package com.lanagj.adviseme.controller.movie;

import com.lanagj.adviseme.controller.user.UserService;
import com.lanagj.adviseme.controller.user.dto.UserMovieDtoIn;
import com.lanagj.adviseme.entity.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping
@AllArgsConstructor
public class MovieController {

    UserService userService;

    @GetMapping
    public String getMovies() {
        return "index";
    }

    @GetMapping("/movies/{movieId}")
    public ResponseEntity<Object> getUserProfile(ModelAndView model, @PathVariable String movieId) {

        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
}
