package com.task5.task5.managers;

import com.task5.task5.domain.FileInfo;

import java.nio.file.Path;
import java.util.ArrayList;

public interface IFileManager {
    ArrayList<FileInfo> getFileInfo (Path path);
}
