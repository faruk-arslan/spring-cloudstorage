package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import org.springframework.stereotype.Service;

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
        return credentialMapper.addCredential(newCredential);
    }

    public List<Credential> getCredentials(int userId){
        return credentialMapper.getCredentials(userId);
    }

    public Credential getCredential(int id){
        return credentialMapper.getCredentialById(id);
    }

    public int updateCredential(int id, Credential updatedCredential){
        return credentialMapper.updateCredential(id, updatedCredential);
    }

    public int deleteCredential(int id){
        return credentialMapper.deleteCredential(id);
    }


}
