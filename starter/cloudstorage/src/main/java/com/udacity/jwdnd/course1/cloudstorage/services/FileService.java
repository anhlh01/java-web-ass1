package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private UserService userService;
    private FileMapper fileMapper;

    public FileService(UserService userService, FileMapper fileMapper) {
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    public int insertFile(Authentication authentication, MultipartFile requestFile) {
        int userId = getUserId(authentication);
        if (userId == -1)
            return -1;
        try {
            //1 MB = 1024 KB = 1024 B
            long fileSizeInMB = requestFile.getSize() / (1024 * 1024);
            if (fileSizeInMB > 1) {
                return -1;
            }
            String fileSizeInString = requestFile.getSize() + "";
            File file = new File(requestFile.getOriginalFilename(), requestFile.getContentType(), fileSizeInString, userId, requestFile.getBytes());
            if (getFileByName(userId, requestFile.getOriginalFilename()) == null) {
                return fileMapper.insertFile(file);
            }
            return 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteFile(int fileId) {
        return fileMapper.deleteFile(fileId);
    }

    private File getFileByName(int userId, String fileName) {
        return fileMapper.getFileByName(userId, fileName);
    }

    public List<File> getFiles(Authentication authentication) {
        int userId = getUserId(authentication);
        if (userId == -1)
            return null;
        return fileMapper.getAllFiles(userId);
    }

    private int getUserId(Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        if (user == null) {
            return -1;
        }
        return user.getUserId();
    }
}
