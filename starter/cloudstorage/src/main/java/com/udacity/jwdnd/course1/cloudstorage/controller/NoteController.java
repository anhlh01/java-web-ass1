package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public String addNote(Authentication authentication, @RequestParam String noteId, @RequestParam String noteTitle, @RequestParam String noteDescription, Model model) {
        String text = "Create note successfully.";
        if (noteService.createNote(authentication, noteId, noteTitle, noteDescription) < 1) {
            text = "Note creation failed.";
        }
        model.addAttribute("notes", noteService.getAllNotes(authentication));
        model.addAttribute("text", text);
        return "home";
    }

    @GetMapping("/{noteId}")
    public String updateNote(@PathVariable int noteId, Model model, Authentication authentication) {
        int result = noteService.deleteNote(noteId);
        String text = "Failed to delete note " + noteId;
        if (result == 1) {
            text = "Successfully deleted note " + noteId;
        }
        model.addAttribute("text", text);
        model.addAttribute("notes", noteService.getAllNotes(authentication));
        return "home";
    }

    @GetMapping
    public String getNotes(Authentication authentication, Model model) {
        model.addAttribute("text", "note");
        model.addAttribute("notes", noteService.getAllNotes(authentication));
        return "home";
    }
}
