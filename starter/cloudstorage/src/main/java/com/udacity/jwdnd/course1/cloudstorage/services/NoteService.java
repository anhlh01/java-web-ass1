package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;
    private UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public List<Note> getAllNotes(Authentication authentication) {
        int userId = getUserId(authentication);
        if (userId == -1) {
            return null;
        }
        return noteMapper.getNotes(userId);
    }

    public int createNote(Authentication authentication, String noteId, String title, String description) {
        int userId = getUserId(authentication);
        if (userId == -1) {
            return -1;
        }
        if (!noteId.isEmpty()) {
            int id = Integer.parseInt(noteId);
            if (noteMapper.getNoteById(id) != null) {
                return noteMapper.updateNote(title, description);
            }
        }
        Note note = new Note(0, title, description, userId);
        return noteMapper.insertNote(note);
    }

    public int deleteNote(int noteId) {
        return noteMapper.deleteNote(noteId);
    }

    private int getUserId(Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        if (user == null) {
            return -1;
        }
        return user.getUserId();
    }
}
