package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int addFile(File file);

    @Select("SELECT * FROM FILES WHERE fileid=#{id}")
    File getFileById(int id);

    @Select("SELECT * FROM FILES WHERE userid=#{userId}")
    List<File> getFiles(int userId);

    @Delete("DELETE FROM FILES WHERE fileid=#{id}")
    int deleteFile(int id);
}
