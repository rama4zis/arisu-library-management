package com.arisu_library.arisu_library_management.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${app.upload-directory}")
    private String uploadDirectory;

    public String uploadFile(String fileName, MultipartFile multipartFile) {

        // Check directory
        Path baseDir = Path.of(uploadDirectory).toAbsolutePath().normalize();
        try {
            Files.createDirectories(baseDir);
            String fileExtension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            String outputFileName = fileName.trim().toLowerCase() + fileExtension;
            Path targetLocation = baseDir.resolve(outputFileName);
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return targetLocation.toString();
        } catch(IOException ex) {
            throw new RuntimeException("Invalid upload directory", ex);
        }
    }   
}