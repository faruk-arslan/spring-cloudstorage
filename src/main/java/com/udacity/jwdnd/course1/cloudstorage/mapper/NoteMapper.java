package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid)" +
            "VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int addNote(Note note);

    @Select("SELECT * FROM NOTES WHERE noteid=#{noteId}")
    Note getNoteById(int noteId);

    @Select("SELECT * FROM NOTES")
    List<Note> getNotes();

    @Update("UPDATE NOTES SET notetitle=#{note.notetitle}, notedescription=#{note.notedescription} " +
            "WHERE noteid=#{id}")
    int updateNote(@Param("id") int noteId, @Param("note") Note note);

    @Delete("DELETE FROM NOTES WHERE noteid=#{noteId}")
    int deleteNote(int noteId);
}
