package com.example.star_repository;


import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController

public class RepositoryController {

//    private static final String REPOSITORY_PATH = "repository/";
    private static final String REPOSITORY_PATH = "/Users/Sofia.Kondirova/.m2/stars_artefacts/";

    //Download an artifact from the repository
   @GetMapping("repository/{*fullPath}")
//    @GetMapping("/Users/Sofia.Kondirova/.m2/stars_artefacts")
    public ResponseEntity<Resource> downloadByGAV(
            @PathVariable String fullPath

    ) {
        String[] parts = fullPath.split("/");
        String groupId = String.join("/", Arrays.copyOfRange(parts, 0, parts.length - 2));
        String artifactId = parts[parts.length - 2];
        String version = parts[parts.length - 1];

        String fileName = groupId + "/" + artifactId + "/" + version;
//        String fileName = groupId + "/" + artifactId + "/" + version + "/" + artifactId + "-" + version + ".jar";
        File file = new File(REPOSITORY_PATH + fileName);

        if (file.exists()) {
            Resource resource = new FileSystemResource(file);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //Load artifacts to the repository
    @PutMapping("repository/{*path}")
    public ResponseEntity<String> uploadFile(@PathVariable String path, InputStream inputStream) {
        try {
            File targetFile = new File(REPOSITORY_PATH + path);
            File dir = targetFile.getParentFile();

            if (!dir.exists()) {
                dir.mkdirs();
            }

            try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file: " + e.getMessage());
        }
    }

    @GetMapping("/libraries")
    public String getLibraries(Model model) {
        File repoDir = new File(REPOSITORY_PATH);
        if (!repoDir.exists() || !repoDir.isDirectory()) {
            model.addAttribute("libraries", new ArrayList<>());
            return "libraries";
        }

        List<String> libraries = Arrays.stream(repoDir.listFiles())
                .filter(File::isFile)
                .map(File::getName)
                .collect(Collectors.toList());

        model.addAttribute("libraries", libraries);
        return "libraries";
    }

}

