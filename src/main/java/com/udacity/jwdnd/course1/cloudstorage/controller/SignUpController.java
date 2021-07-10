package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    private UserService userService;

    private boolean ifSucceed;
    private String feedbackMessage;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getSignUpPage(@ModelAttribute("user") User user, Model model,@ModelAttribute("ifSucceeded") String ifSucceededFlash,
                                @ModelAttribute("feedbackMessage") String feedbackMessageFlash){
        return "signup";
    }

    @PostMapping
    public String signUpUser(@ModelAttribute("user") User user, Model model, RedirectAttributes attributes){

        if(userService.isUsernameExist(user.getUsername())){
            this.ifSucceed=false;
            this.feedbackMessage="Username already exist.";
        }else{
            int rowsAdded=userService.createUser(user);
            if(rowsAdded<0){
                this.ifSucceed=false;
                this.feedbackMessage="Something went wrong when creating user.";
            }else{
                this.ifSucceed=true;
                this.feedbackMessage="User successfully created, you can log in.";
                // Show the error message on login page (not on signup page).
                attributes.addFlashAttribute("ifSucceeded", this.ifSucceed);
                attributes.addFlashAttribute("feedbackMessage", this.feedbackMessage);
                return "redirect:/login";
            }
        }
        // Show the remaining error messages on signup page,
        // because the registration has not finished yet.
        attributes.addFlashAttribute("ifSucceeded", this.ifSucceed);
        attributes.addFlashAttribute("feedbackMessage", this.feedbackMessage);
        return "redirect:/signup";
    }
}
