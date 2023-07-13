package com.HelloSpring.service;

import org.springframework.web.multipart.MultipartFile;
import com.HelloSpring.model.*;

import java.io.IOException;

public interface DocumentService
{
    public String uploadFileSystem(MultipartFile file , int customerId , DocumentType type) throws IOException;
    public byte[] downloadImage(int customerId,DocumentType Doc) throws IOException;
}
