package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public  int addNewFile(File newFile){
        return fileMapper.addFile(newFile);
    }
    public File getFile(int id){
        return fileMapper.getFileById(id);
    }
    public List<File> getFiles(int userId){
        return fileMapper.getFiles(userId);
    }
    // TODO: Implement update in mapper and here
    public int deleteFile(int id){
        return fileMapper.deleteFile(id);
    }
}
