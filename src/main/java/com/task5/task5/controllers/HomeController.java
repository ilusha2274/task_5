package com.task5.task5.controllers;

import com.task5.task5.managers.IFileManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.nio.file.Path;

@Controller
public class HomeController {

    private final IFileManager fileManager;

    public HomeController(IFileManager fileManager) {
        this.fileManager = fileManager;
    }

    @GetMapping("/")
    public String printHome(String path, Model model) {
        model.addAttribute("path", "");
        model.addAttribute("listFile", fileManager.returnAllDisk());

        return "home";
    }

    @PostMapping("/next")
    public String printNext(String path, Model model, String nameFile) {
        String fileSeparator = File.separator;
        Path newPath;

        if (path.equals(""))
            newPath = Path.of(path + nameFile);
        else
            newPath = Path.of(path + fileSeparator + nameFile);

        model.addAttribute("listFile", fileManager.getFileInfo(newPath));
        model.addAttribute("path", newPath);

        return "home";
    }

    @PostMapping("/back")
    public String printPrevious(String path, Model model) {
        Path newPath = Path.of(path).getParent();

        if (newPath == null) return "redirect:/";

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
