package com.lanagj.adviseme.controller.evalluation;

import com.lanagj.adviseme.controller.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;

@Controller
@AllArgsConstructor
@RolesAllowed({"ROLE_ADMIN"})
public class EvaluationController {
}
