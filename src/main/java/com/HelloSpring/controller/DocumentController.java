package com.HelloSpring.controller;

import com.HelloSpring.apiresponse.ApiResponse;
import com.HelloSpring.model.DocumentType;
import com.HelloSpring.repo.CustomerRepo;
import com.HelloSpring.repo.DocumentRepoJpaRepository;
import com.HelloSpring.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
public class DocumentController
{
    @Autowired
    private DocumentRepoJpaRepository pr;

    @Autowired
    private DocumentService ds;

    @Autowired
    private CustomerRepo cr;

    @PostMapping(value = "/uploadDocuments/{customerId}")
    public String uploadFileSystem(@RequestParam("file") MultipartFile file, @PathVariable int customerId,@RequestParam("type") DocumentType type) throws IOException
    {
        log.info("hi");
        String uploadImage = ds.uploadFileSystem(file,customerId,type);
        return "Image Uploaded Successfully";
    }

    @GetMapping(value = "/Documents/{customerId}/{Doc}")
    public ResponseEntity<byte[]>downloadImage(@PathVariable  int customerId ,@PathVariable DocumentType Doc) throws IOException {
      byte[] imageData = ds.downloadImage(customerId,Doc);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
    }


}
