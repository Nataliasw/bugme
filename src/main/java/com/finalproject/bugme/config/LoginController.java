package com.finalproject.bugme.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @GetMapping ("/login")
    public String index(){
        return "start/login";
    }
}
