package com.example.springsecuritydemo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class HomeController {
        @GetMapping("/")
        public ModelAndView home() {
            return new ModelAndView("redirect:/login");
        }

        @GetMapping("/login")
        public ModelAndView login() {
            return new ModelAndView("login");
        }
    @GetMapping("/access-denied")
    public ModelAndView accessDenied(Authentication authentication) {
        ModelAndView mav = new ModelAndView("access-denied");

        if (authentication != null && authentication.isAuthenticated()) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            mav.addObject("role", isAdmin ? "ADMIN" : "USER");
        }

        return mav;
    }



    @GetMapping("/default")
        public String redirectAfterLogin(Authentication authentication) {
            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin";
            } else {
                return "redirect:/user";
            }
        }

        @GetMapping("/user")
        public ModelAndView user(Principal principal) {
            return new ModelAndView("user");
        }

        @GetMapping("/admin")
        public ModelAndView admin() {
            return new ModelAndView("admin");
        }

}
