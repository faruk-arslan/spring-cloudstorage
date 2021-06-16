package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final NoteService noteService;

    public HomeController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String serveHomePage(@ModelAttribute("note") Note note, Model model,
                                @ModelAttribute("ifSucceeded") String ifSucceededFlash,
                                @ModelAttribute("feedbackMessage") String feedbackMessageFlash){
        System.out.println("Flash Attr--->"+ifSucceededFlash);
        System.out.println("Flash Attr--->"+feedbackMessageFlash);
        model.addAttribute("notes", noteService.getNotes());
        return "home";
    }

//    @GetMapping
//    public String getHomePage(){
//        return "home";
//    }
}
