//package com.example.star_repository;
//
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.File;
//
//@RestController
//public class RepositoryController {
//
//    private static final String REPOSITORY_PATH = "repository/";
//
//    @GetMapping("/repository/{artifactId}")
//    public ResponseEntity<Resource> downloadByArtifactId(@PathVariable String artifactId) {
//        String fileName = artifactId + ".jar";
//        File file = new File(REPOSITORY_PATH + fileName);
//
//        if (file.exists()) {
//            Resource resource = new FileSystemResource(file);
//            return ResponseEntity.ok()
//                    .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
//                    .body(resource);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
//}

