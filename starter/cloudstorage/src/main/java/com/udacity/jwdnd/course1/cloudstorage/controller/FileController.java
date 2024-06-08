package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile requestFile, Model model, Authentication authentication) {
        int result = fileService.insertFile(authentication, requestFile);
        String text;
        if (result == 1) {
            text = "Successfully uploaded file " + requestFile.getOriginalFilename();
        } else if (result == 0) {
            text = "Failed to upload 2 file with the same name: " + requestFile.getOriginalFilename();
        } else {
            text = "Can not upload file bigger than 1 MB, file name: " + requestFile.getOriginalFilename();
        }
        model.addAttribute("text", text);
        model.addAttribute("files", fileService.getFiles(authentication));
        return "home";
    }

    @GetMapping("/{fileId}")
    public String deleteFile(@PathVariable("fileId") String fileIdParam, Model model, Authentication authentication) {
        int fileId = Integer.parseInt(fileIdParam);
        int result = fileService.deleteFile(fileId);
        String text = "Failed to delete file " + fileId;
        if (result == 1) {
            text = "Successfully deleted file " + fileId;
        }
        model.addAttribute("text", text);
        model.addAttribute("files", fileService.getFiles(authentication));
        return "home";
    }
}
