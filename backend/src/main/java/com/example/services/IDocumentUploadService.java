package com.example.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IDocumentUploadService {

    void saveFile(String name, MultipartFile file) throws IOException;
}
