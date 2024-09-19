package com.example.star_repository;


import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Arrays;

@RestController

public class RepositoryController {

    private static final String REPOSITORY_PATH = "repository/";

//    @GetMapping("repository/{artifactId}/{version}")
//@GetMapping("repository/{groupId:.+}/{artifactId}/{version}")
//@GetMapping("repository/{groupId}/{artifactId}/{version}")
    @GetMapping("repository/{*fullPath}")
    public ResponseEntity<Resource> downloadByGAV(
//        @PathVariable String groupId,
//        @PathVariable String artifactId,
//        @PathVariable String version
        @PathVariable String fullPath

    ) {
 //      String groupPath = groupId.replace('.', '/');
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
}

