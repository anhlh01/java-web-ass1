package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping
    public String addCredential(Authentication authentication, @RequestParam String credentialId, @RequestParam String username, @RequestParam String password, @RequestParam String url, Model model) {
        String text = "Create credential successfully.";
        int result = credentialService.createCredential(authentication, credentialId, url, username, password);
        if (result == 0) {
            text = "Credential creation failed. Invalid url.";
        } else if (result == -1) {
            text = "Credential creation failed. Something went wrong.";
        }
        model.addAttribute("credentials", credentialService.getAllCredentials(authentication));
        model.addAttribute("text", text);
        return "home";
    }

    @GetMapping("/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, Model model, Authentication authentication) {
        int result = credentialService.deleteCredential(credentialId);
        String text = "Failed to delete credential " + credentialId;
        if (result == 1) {
            text = "Successfully deleted credential " + credentialId;
        }
        model.addAttribute("text", text);
        model.addAttribute("credentials", credentialService.getAllCredentials(authentication));
        return "home";
    }

    @GetMapping
    public String getCredentials(Authentication authentication, Model model) {
        model.addAttribute("text", "credential");
        model.addAttribute("credentials", credentialService.getAllCredentials(authentication));
        return "home";
    }
}
