package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String getLoginPage(@ModelAttribute("ifSucceeded") String ifSucceededFlash,
                               @ModelAttribute("feedbackMessage") String feedbackMessageFlash){
//        System.out.println("Flash Attr Login--->"+ifSucceededFlash);
//        System.out.println("Flash Attr Login--->"+feedbackMessageFlash);
        return "login";
    }
    // Logout handled in SecurityConfig class, below code is an alternative.
//    @PostMapping("/logout")
//    public String logoutUser(HttpServletRequest request, HttpServletResponse response){
//        System.out.println("Here the LOGOUT POST request.");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null){
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/login?logout";
//    }
}
