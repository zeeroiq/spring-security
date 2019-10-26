package com.shri.springsecurity.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: ZeeroIQ
 * @Date: 10/26/2019 7:32 PM
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String list() {
        return "loginPage";
    }
}
