package com.task5.task5.controllers;

import com.task5.task5.domain.FileInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
public class HomeController {

    static final private Path startPath = Path.of("testDirectory").toAbsolutePath();
    static final private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @GetMapping("/")
    public String printHome(String path, Model model, String nameFile){
        model.addAttribute("listFile", getFileInfo(startPath));
        model.addAttribute("path", startPath);
        return "home";
    }

    @PostMapping("/next")
    public String printNext(String path, Model model, String nameFile){
        String fileSeparator = File.separator;
        Path newPath = Path.of(path + fileSeparator + nameFile);
        model.addAttribute("listFile", getFileInfo(newPath));
        model.addAttribute("path", newPath);
        return "home";
    }

    @PostMapping("/back")
    public String printPrevious(String path, Model model){
        if (path.equals(startPath.toString())){
            return "redirect:/";
        }
        Path newPath = Path.of(path).getParent();
        model.addAttribute("listFile", getFileInfo(newPath));
        model.addAttribute("path", newPath);
        return "home";
    }

    private ArrayList<FileInfo> getFileInfo(Path path){
        ArrayList<FileInfo> arr = new ArrayList<>();
        try {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);
            for (Path p : directoryStream){
                if (Files.isDirectory(p)){
                    arr.add(new FileInfo(true,p.getFileName().toString(),"",getDateCreate(p),getDateUpdate(p)));
                }else {
                    arr.add(new FileInfo(false,p.getFileName().toString(),getSizeFile(p),getDateCreate(p),getDateUpdate(p)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    private String getDateCreate(Path path) throws IOException {
        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
        return dateTimeFormatter.format(attr.creationTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    private String getDateUpdate(Path path) throws IOException {
        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
        return dateTimeFormatter.format(attr.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    private String getSizeFile(Path path) throws IOException {
        long size = Files.size(path);
        if (size < 1024) return size + " B";
        int z = (63 - Long.numberOfLeadingZeros(size)) / 10;
        return String.format("%.1f %sB", (double)size / (1L << (z*10)), " KMGTPE".charAt(z));
    }
}
