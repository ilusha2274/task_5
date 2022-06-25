package com.task5.task5.managers;

import com.task5.task5.domain.FileInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FileManager implements IFileManager {
    static final private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public ArrayList<FileInfo> getFileInfo(Path path) {
        ArrayList<FileInfo> arr = new ArrayList<>();
        try {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);
            for (Path p : directoryStream) {
                if (Files.isDirectory(p)) {
                    arr.add(0, new FileInfo(true, p.getFileName().toString(), "", getDateCreate(p), getDateUpdate(p)));
                } else {
                    arr.add(new FileInfo(false, p.getFileName().toString(), getSizeFile(p), getDateCreate(p), getDateUpdate(p)));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return arr;
    }

    @Override
    public ArrayList<FileInfo> returnAllDisk() {
        File[] paths = File.listRoots();

        ArrayList<FileInfo> fileInfo = new ArrayList<>();
        for (File path : paths) {
            fileInfo.add(new FileInfo(true, path.toString(), "", "", ""));
        }
        return fileInfo;
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
        return String.format("%.1f %sB", (double) size / (1L << (z * 10)), " KMGTPE".charAt(z));
    }
}
