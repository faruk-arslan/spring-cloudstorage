package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notes")
public class NoteController {
    // TODO: Check errors and send to the front-end.
    // TODO: Get logged in user id.
    // TODO: Implement try-cache for db operations.
    private boolean ifSucceed;
    private String feedbackMessage;

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public String getNotes(@ModelAttribute("note") Note note, Model model, Authentication authentication){

        model.addAttribute("notes",noteService.getNotes(userService.getUser(authentication.getName()).getUserid()));
        return "fragments/note-list";
    }

    @PostMapping("add")
    public String addNote(@ModelAttribute("note") Note note, Model model, RedirectAttributes attributes, Authentication authentication){
        note.setUserid(userService.getUser(authentication.getName()).getUserid());
        // Check if the title/description is empty before calling the service.
        if (note.getNotedescription()==null){
            this.ifSucceed=false;
            this.feedbackMessage="Note content can not be empty";
        }
        // Add the new note.
        int rowsAdded=noteService.addNewNote(note);
        if (rowsAdded<0) {
            this.ifSucceed=false;
            this.feedbackMessage="Something went wrong when adding the note.";
        }
        else {
            this.ifSucceed=true;
            this.feedbackMessage="Note has been added.";
        }
        attributes.addFlashAttribute("ifSucceeded", this.ifSucceed);
        attributes.addFlashAttribute("feedbackMessage", this.feedbackMessage);
//        attributes.addAttribute("attribute", "redirectWithRedirectView");
        return "redirect:/home";
    }

    @PostMapping("edit/{id}")
    public String editNote(@PathVariable int id,@ModelAttribute("note") Note note, Model model){
        int rowsUpdated=noteService.updateNote(id, note);
        if (rowsUpdated<0) {
            this.ifSucceed=false;
            this.feedbackMessage="Something went wrong when updating the note.";
        }
        else {
            this.ifSucceed=true;
            this.feedbackMessage="Note has been updated.";
        }
        return "redirect:/home";
    }

    @PostMapping("delete/{id}")
    @ResponseBody
    public String deleteNote(@PathVariable int id,@ModelAttribute("note") Note note, Model model, RedirectAttributes redirectAttributes){
        int rowsDeleted=noteService.deleteNote(id);
        if (rowsDeleted<0) {
            this.ifSucceed=false;
            this.feedbackMessage="Something went wrong when deleting the note.";
        }
        else {
            this.ifSucceed=true;
            this.feedbackMessage="Note has been deleted.";
        }
//        return "redirect:/home";
        return "something";
    }

}
