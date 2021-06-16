package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.apache.ibatis.logging.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.Statement;
import java.util.List;

@Service
public class TestService {
    private UserMapper userMapper;
    private NoteMapper noteMapper;
    private FileMapper fileMapper;
    private CredentialMapper credentialMapper;
    private User currentUser;

    public TestService(UserMapper userMapper, NoteMapper noteMapper, FileMapper fileMapper,
                       CredentialMapper credentialMapper) {
        this.userMapper = userMapper;
        this.noteMapper=noteMapper;
        this.fileMapper=fileMapper;
        this.credentialMapper=credentialMapper;
    }

    @Bean
    public void testFunction(){
        testUser();
        testNote();
        //testFile();
        //testCredential();
    }

    public void testUser(){
        // create a user to test db
        User user=new User();
        user.setUsername("username");
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setPassword("encrypted-password");
        user.setSalt("salt");
        System.out.println("\nAdding test user...");
        userMapper.addUser(user);
        // get user
        User userGet;
        System.out.println("Getting user...");
        userGet=userMapper.getUser("username");
        System.out.println("\n----------Get user----------\n" +
                "Username: " + userGet.getUsername() + "\n" +
                "Firstname: " + userGet.getFirstname() + "\n" +
                "Lastname: " + userGet.getLastname() + "\n" +
                "Password: " + userGet.getPassword() + "\n" +
                "Salt: " + userGet.getSalt() + "\n");
        // assign current user to use in another functions
        this.currentUser=userGet;
    }

    public void testNote(){
        // create a note for test
        Note note=new Note();
        note.setNotetitle("My Title");
        note.setNotedescription("Some description for my note...");
        note.setUserid(currentUser.getUserid());
        System.out.println("New note ID (not added): "+note.getNoteid() + "\n");
        System.out.println("Adding test note...");
        System.out.println("ADD RETURN-->"+noteMapper.addNote(note));

        System.out.println("New note ID: "+note.getNoteid() + "\n");
        System.out.println("Adding test note...");
        noteMapper.addNote(note);
        System.out.println("New note ID: "+note.getNoteid() + "\n");
        // get note by id
        Note noteGetID;
        noteGetID=noteMapper.getNoteById(note.getNoteid());

        System.out.println("----------Get note by id----------\n" +
                "ID: " + noteGetID.getNoteid() + "\n" +
                "Title: " + noteGetID.getNotetitle() + "\n" +
                "Description: " + noteGetID.getNotedescription() +"\n");
        // get all notes
        List<Note> notes=noteMapper.getNotes();
        System.out.println("----------All Notes-----------");
        for(Note n : notes){
            System.out.println("Note with id:" + n.getNoteid() + "\n" +
                    "Title: " + n.getNotetitle() + "\n" +
                    "Description: " + n.getNotedescription() + "\n" +
                    "----------");
        }
        System.out.println("----------END----------\n");

        // update note
        note.setNotetitle("Changed Title");
        note.setNotedescription("Changed description...");
        noteMapper.updateNote(2, note);
        System.out.println("Note with id 2 is updated. \n");

        notes=noteMapper.getNotes();
        System.out.println("----------All Notes (UPDATED)-----------");
        for(Note n : notes){
            System.out.println("Note with id:" + n.getNoteid() + "\n" +
                    "Title: " + n.getNotetitle() + "\n" +
                    "Description: " + n.getNotedescription() + "\n" +
                    "----------");
        }
        System.out.println("----------END----------\n");

        // delete note
        noteMapper.deleteNote(1);
        System.out.println("Note with id 1 is deleted. \n");
        notes=noteMapper.getNotes();
        System.out.println("----------All Notes (DELETED)-----------");
        for(Note n : notes){
            System.out.println("Note with id:" + n.getNoteid() + "\n" +
                    "Title: " + n.getNotetitle() + "\n" +
                    "Description: " + n.getNotedescription() + "\n" +
                    "----------");
        }
        System.out.println("----------END----------\n");
    }

    public void testFile(){
        File file = new File();
        file.setFilename("File Name");
        // TODO: Set the file data (BLOB).
        file.setFilesize("1536KB");
        file.setContenttype(".jpg");
        file.setUserid(currentUser.getUserid());

        fileMapper.addFile(file);
        System.out.println("File added \n");
    }

    public void testCredential(){
        // add a credential
        Credential cr=new Credential();
        cr.setUrl("https://google.com");
        cr.setUsername("username_cr");
        cr.setKey_("key");
        cr.setPassword("password_cr");
        cr.setUserid(currentUser.getUserid());

        credentialMapper.addCredential(cr);
        System.out.println("Credential added \n");
        credentialMapper.addCredential(cr);
        System.out.println("Credential added \n");

        // get credential by id
        Credential crGetId=credentialMapper.getCredentialById(1);
        System.out.println("----------Get credential by id----------\n" +
                "Credential ID: " + crGetId.getCredentialid() + "\n" +
                "URL: " + crGetId.getUrl() + "\n" +
                "Username: " + crGetId.getUsername() + "\n" +
                "Key: " + crGetId.getKey_() + "\n" +
                "Password: " + crGetId.getPassword() + "\n");

        // get all credentials
        List<Credential> credentials=credentialMapper.getCredentials();
        System.out.println("----------All Credentials----------\n");
        for(Credential c : credentials){
            System.out.println("Credential ID: " + c.getCredentialid() + "\n" +
                    "URL: " + c.getUrl() + "\n" +
                    "Username: " + c.getUsername() + "\n" +
                    "Key: " + c.getKey_() + "\n" +
                    "Password: " + c.getPassword() +
                    "\n----------");
        }
        System.out.println("\n----------END----------\n");

        // update credential
        crGetId.setKey_("key_changed");
        crGetId.setPassword("password_cr_changed");
        crGetId.setUsername("username_cr_changed");
        credentialMapper.updateCredential(2, crGetId);
        System.out.println("Credential with id 2 is updated\n");

        System.out.println("----------All Credentials (UPDATED)----------\n");
        credentials=credentialMapper.getCredentials();
        for(Credential c : credentials){
            System.out.println("Credential ID: " + c.getCredentialid() + "\n" +
                    "URL: " + c.getUrl() + "\n" +
                    "Username: " + c.getUsername() + "\n" +
                    "Key: " + c.getKey_() + "\n" +
                    "Password: " + c.getPassword() +
                    "\n----------");
        }
        System.out.println("\n----------END----------\n");

        // delete credential
        credentialMapper.deleteCredential(1);
        System.out.println("Credential with id 1 is deleted\n");

        System.out.println("----------All Credentials (DELETED)----------\n");
        credentials=credentialMapper.getCredentials();
        for(Credential c : credentials){
            System.out.println("Credential ID: " + c.getCredentialid() + "\n" +
                    "URL: " + c.getUrl() + "\n" +
                    "Username: " + c.getUsername() + "\n" +
                    "Key: " + c.getKey_() + "\n" +
                    "Password: " + c.getPassword() +
                    "\n----------");
        }
        System.out.println("\n----------END----------\n");
    }

}
