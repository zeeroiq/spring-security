package com.shri.springsecurity.web.controller;

import com.shri.springsecurity.persistence.UserRepository;
import com.shri.springsecurity.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @Author: ZeeroIQ
 * @Date: 10/26/2019 9:11 PM
 */
@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping(value = "signup")
    public ModelAndView registrationForm() {
        return new ModelAndView("registraionPage", "user", new User());
    }

    @GetMapping("/user/register")
    public ModelAndView registerUser(@Valid final User user, final BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("registrationPage", "user", user);
        }

        userRepo.save(user);

        return new ModelAndView("redirect:/login");
    }
}
