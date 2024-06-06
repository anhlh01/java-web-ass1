package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private UserService userService;
    private HashService hashService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService, HashService hashService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
        this.hashService = hashService;
    }

    public List<Credential> getAllCredentials(Authentication authentication) {
        int userId = getUserId(authentication);
        if (userId == -1) {
            return null;
        }
        return credentialMapper.getCredentials(userId);
    }

    private boolean isValidURL(String url) throws MalformedURLException, URISyntaxException {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    public int createCredential(Authentication authentication, String credentialId, String url, String username, String password) {
        int userId = getUserId(authentication);
        if (userId == -1) {
            return -1;
        }
        try {
            if (!isValidURL(url)) {
                return 0;
            }
        } catch (Exception e) {
            return -1;
        }


        if (!credentialId.isEmpty()) {
            int id = Integer.parseInt(credentialId);
            if (credentialMapper.getCredentialById(id) != null) {
                return credentialMapper.updateCredential(url, username, password);
            }
        }
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String key = hashService.getHashedValue(password, encodedSalt);
        Credential credential = new Credential(0, url, username, key, password, userId);
        return credentialMapper.insertCredential(credential);
    }

    public int deleteCredential(int credentialId) {
        return credentialMapper.deleteCredential(credentialId);
    }

    private int getUserId(Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        if (user == null) {
            return -1;
        }
        return user.getUserId();
    }
}
