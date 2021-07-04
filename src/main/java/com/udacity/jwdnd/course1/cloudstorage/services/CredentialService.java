package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;
    // TODO: Add a function to return decrypted credential.

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int addNewCredential(Credential newCredential){
        // TODO: Encrypt the new credential.
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(newCredential.getPassword(), encodedKey);
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);

        newCredential.setPassword(encryptedPassword);
        newCredential.setkey2(encodedKey);

        return credentialMapper.addCredential(newCredential);
    }

    public String getPlainPass(int id){
        Credential cr=credentialMapper.getCredentialById(id);
        return encryptionService.decryptValue(cr.getPassword(), cr.getkey2());
    }

    public List<Credential> getCredentials(int userId){
        return credentialMapper.getCredentials(userId);
    }

    public Credential getCredential(int id){
        return credentialMapper.getCredentialById(id);
    }

    public int updateCredential(int id, Credential updatedCredential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(updatedCredential.getPassword(), encodedKey);

        updatedCredential.setPassword(encryptedPassword);
        updatedCredential.setkey2(encodedKey);

        return credentialMapper.updateCredential(id, updatedCredential);
    }

    public int deleteCredential(int id){
        return credentialMapper.deleteCredential(id);
    }


}
