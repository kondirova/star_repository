package com.example.star_repository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@SpringBootApplication
public class StarRepositoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarRepositoryApplication.class, args);
	}

	@RestController
	public class RepositoryController {

		private static final String REPOSITORY_PATH = "repository/";

		@GetMapping("repository/{groupId}/{artifactId}/{version}/{libraryName}")
		public ResponseEntity<Resource> downloadByGAV(
				@PathVariable String artifactId,
				@PathVariable String groupId,
				@PathVariable String version,
				@PathVariable String libraryName
				) {
			String fileName = groupId + "/" + artifactId + "/" + version + "/" + libraryName + ".jar";
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
}
