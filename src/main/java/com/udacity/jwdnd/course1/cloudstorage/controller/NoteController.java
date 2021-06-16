package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.apache.ibatis.annotations.Delete;
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

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String getNotes(@ModelAttribute("note") Note note, Model model){
        model.addAttribute("notes",noteService.getNotes());
        return "fragments/note-list";
    }

    @PostMapping("add")
    public String addNote(@ModelAttribute("note") Note note, Model model, RedirectAttributes attributes){
        note.setUserid(1);
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
//        attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
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
        return "redirect:/home";
    }

}
