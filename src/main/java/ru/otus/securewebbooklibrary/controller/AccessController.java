package ru.otus.securewebbooklibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessController {
    @GetMapping("/accessDenied")
    public String denyAccess() {
        return "accessDenied";
    }
}
