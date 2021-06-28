package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileController {

    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping
    public String getFiles(@ModelAttribute("file") File file, Model model, Authentication authentication){
        model.addAttribute("files", fileService.getFiles(userService.getUser(authentication.getName()).getUserid()));
        return "fragments/file-list";
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, RedirectAttributes redirectAttributes, Authentication authentication) throws IOException {
        System.out.println(file.getSize());
        // Create file object.
        File newFile=new File();
        newFile.setUserid(userService.getUser(authentication.getName()).getUserid());
        newFile.setFilesize(file.getSize());
        newFile.setFilename(file.getOriginalFilename());
        newFile.setContenttype(file.getContentType());
        newFile.setFiledata(file.getBytes());
        // Add file via FileService.
        fileService.addNewFile(newFile);
        return "redirect:/home";
    }

    @GetMapping("download")
    @ResponseBody
    public ResponseEntity<Resource> download(@RequestParam int fileId){
         System.out.println(fileId);
         File file=fileService.getFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(  file.getContenttype() ))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFiledata()));
    }
}
