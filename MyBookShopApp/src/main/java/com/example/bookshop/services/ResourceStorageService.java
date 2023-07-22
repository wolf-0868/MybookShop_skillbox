package com.example.bookshop.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log
@Service
@RequiredArgsConstructor
public class ResourceStorageService {

    @Value("${upload.path}")
    private String uploadPath;

    public String saveNewBookImage(MultipartFile aFile, String aSlug) throws IOException {
        String resourcesURL = null;
        if (!aFile.isEmpty()) {
            if (!new File(uploadPath).exists()) {
                Files.createDirectories(Paths.get(uploadPath));
                log.info("Created image folder in " + uploadPath);
            }
            String filename = aSlug + "." + FilenameUtils.getExtension(aFile.getOriginalFilename());
            Path path = Paths.get(uploadPath, filename);
            resourcesURL = "/book-covers/" + filename;
            aFile.transferTo(path);
            log.info(filename + " uploaded OK!");
        }
        return resourcesURL;
    }

}
