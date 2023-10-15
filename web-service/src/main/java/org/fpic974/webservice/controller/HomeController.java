package org.fpic974.webservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String home() {
        return "redirect:http://localhost:8080/web/patient";
    }
}
