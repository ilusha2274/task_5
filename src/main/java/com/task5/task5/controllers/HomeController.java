package com.task5.task5.controllers;

import com.task5.task5.managers.IFileManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {

    private final IFileManager fileManager;

    public HomeController(IFileManager fileManager) {
        this.fileManager = fileManager;
    }

    static final private Path startPath = Path.of("testDirectory").toAbsolutePath();
    static final private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @GetMapping("/")
    public String printHome(String path, Model model, String nameFile) {
        model.addAttribute("listFile", fileManager.getFileInfo(startPath));
        model.addAttribute("path", startPath);
        return "home";
    }

    @PostMapping("/next")
    public String printNext(String path, Model model, String nameFile) {
        String fileSeparator = File.separator;
        Path newPath = Path.of(path + fileSeparator + nameFile);
        model.addAttribute("listFile", fileManager.getFileInfo(newPath));
        model.addAttribute("path", newPath);
        return "home";
    }

    @PostMapping("/back")
    public String printPrevious(String path, Model model) {
//        if (path.equals(startPath.toString())){
//            return "redirect:/";
//        }

        Path newPath = Path.of(path).getParent();
        if (newPath == null){
            model.addAttribute("path", path);
            model.addAttribute("listFile", fileManager.getFileInfo(Path.of(path)));
            model.addAttribute("exception", "Это корневая директория!");
            return "home";
        }
        model.addAttribute("listFile", fileManager.getFileInfo(newPath));
        model.addAttribute("path", newPath);
        return "home";
    }

    @PostMapping("/")
    public String printInput(String newPath, String oldPath, Model model) {
        try {
            model.addAttribute("path", newPath);
            model.addAttribute("listFile", fileManager.getFileInfo(Path.of(newPath)));
        } catch (RuntimeException e) {
            model.addAttribute("path", oldPath);
            model.addAttribute("listFile", fileManager.getFileInfo(Path.of(oldPath)));
            model.addAttribute("exception", "Неверно введен Path!");
            return "home";
        }
        return "home";
    }
}
