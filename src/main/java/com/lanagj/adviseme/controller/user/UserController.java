package com.lanagj.adviseme.controller.user;

import com.lanagj.adviseme.controller.user.dto.UserMovieDtoIn;
import com.lanagj.adviseme.entity.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    UserService userService;

    @GetMapping
    public ModelAndView getUserProfile(ModelAndView model) {

        User simpleUser = this.userService.getSimpleUser();
        model.addObject("user", simpleUser);

        model.setViewName("user_profile");

        return model;
    }

    @PostMapping
    public void addMovieRating(@RequestBody UserMovieDtoIn dto) {
        this.userService.addMovieRating(dto.getUser_id(), dto.getMovie_id(), dto.getRating());
    }
}
