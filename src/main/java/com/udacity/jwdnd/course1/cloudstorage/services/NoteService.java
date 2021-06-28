package com.udacity.jwdnd.course1.cloudstorage.services;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;
    HashMap<String, String> responseMap=new HashMap<>();
    //TODO: Evaluate return types.
    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNewNote(Note newNote){
        return noteMapper.addNote(newNote);
    }

    public Note getNote(int id){
        return noteMapper.getNoteById(id);
    }

    public List<Note> getNotes(int userId){
        return noteMapper.getNotes(userId);
    }

    public int updateNote(int id, Note updatedNote){
        return noteMapper.updateNote(id, updatedNote);
    }

    public int deleteNote(int id){
        return noteMapper.deleteNote(id);
    }
}
