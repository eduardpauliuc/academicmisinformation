package com.example.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
@AllArgsConstructor
public class ContractUploadService implements IDocumentUploadService {
    @Override
    public void saveFile(String name, MultipartFile file) throws IOException {
        Path uploadDirectory = Paths.get("Contracts");
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadDirectory.resolve(name);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new IOException("Error saving file.pdf " + name, exception);
        }
    }

}
