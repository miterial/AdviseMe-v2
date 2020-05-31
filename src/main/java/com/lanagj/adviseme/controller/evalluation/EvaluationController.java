package com.lanagj.adviseme.controller.evalluation;

import com.lanagj.adviseme.configuration.ApplicationSettings;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class EvaluationController {

    @GetMapping
    public ModelAndView settingsPage(ModelAndView model) {

        model.addObject("algType", ApplicationSettings.getInstance().getType());
        model.setViewName("settings");

        return model;
    }

    @PostMapping
    public void changeAlgorithmType(@RequestBody SettingsDto settingsDto) {
        ApplicationSettings.getInstance().setType(settingsDto.getType());
    }
}
