package com.example.star_repository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    private static final String REPOSITORY_PATH = "/Users/Sofia.Kondirova/.m2/stars_artefacts/";

    @GetMapping("/repository/search")
    public String searchArtifacts(Model model) {
        List<String> artifacts = new ArrayList<>();
        findArtifacts(new File(REPOSITORY_PATH), artifacts);

        model.addAttribute("artifacts", artifacts);
        return "search";
    }

    private void findArtifacts(File directory, List<String> artifacts) {
        if (!directory.exists() || !directory.isDirectory()) {
            return;
        }

        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                findArtifacts(file, artifacts);
            } else if (file.isFile() && file.getName().endsWith(".jar")) {
                // Добавляем относительный путь для отображения
                artifacts.add(file.getAbsolutePath().replace(REPOSITORY_PATH, ""));
            }
        }
    }
}
