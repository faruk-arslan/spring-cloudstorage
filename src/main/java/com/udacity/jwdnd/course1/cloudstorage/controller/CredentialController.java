package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private boolean ifSucceed;
    private String feedbackMessage;

    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getCredentials(@ModelAttribute("credential")Credential credential,
                                 Model model){
        model.addAttribute("credentials",credentialService.getCredentials());
        return "fragments/credential-list";
    }

    @PostMapping("add")
    public String addCredential(@ModelAttribute("credential") Credential credential,
                                Model model, RedirectAttributes attributes){
        // TODO: Insert the correct user id after authentication.
        credential.setUserid(1);
        int rowsAdded=credentialService.addNewCredential(credential);
        if (rowsAdded<0) {
            this.ifSucceed=false;
            this.feedbackMessage="Something went wrong when adding the credential.";
        }
        else {
            this.ifSucceed=true;
            this.feedbackMessage="Credential has been added.";
        }
        attributes.addFlashAttribute("ifSucceeded", this.ifSucceed);
        attributes.addFlashAttribute("feedbackMessage", this.feedbackMessage);

        return "redirect:/home";
    }

    @PostMapping("edit/{id}")
    public String editCredential(@PathVariable int id,
                                 @ModelAttribute("credential") Credential credential,
                                 Model model){
        int rowsUpdated=credentialService.updateCredential(id, credential);
        if(rowsUpdated<0){
            this.ifSucceed=false;
            this.feedbackMessage="Something went wrong when updating the credential.";
        }
        else{
            this.ifSucceed=true;
            this.feedbackMessage="Credential has been updated.";
        }
        return "redirect:/home";
    }

    @PostMapping("/delete/{id}")
    public String deleteCredential(@PathVariable int id,
                                   @ModelAttribute("credential") Credential credential,
                                   Model model, RedirectAttributes redirectAttributes){
        int rowsDeleted=credentialService.deleteCredential(id);
        if (rowsDeleted<0) {
            this.ifSucceed=false;
            this.feedbackMessage="Something went wrong when deleting the credential.";
        }
        else {
            this.ifSucceed=true;
            this.feedbackMessage="Credential has been deleted.";
        }
        return "redirect:/home";
    }

}
