package com.task5.task5.domain;

public class FileInfo {
    private boolean isDirectory;
    private String nameFile;
    private String sizeFile;
    private String dateCreateFile;
    private String dateUpdateFile;

    public FileInfo(boolean isDirectory, String nameFile, String sizeFile, String dateCreateFile, String dateUpdateFile) {
        this.isDirectory = isDirectory;
        this.nameFile = nameFile;
        this.sizeFile = sizeFile;
        this.dateCreateFile = dateCreateFile;
        this.dateUpdateFile = dateUpdateFile;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getSizeFile() {
        return sizeFile;
    }

    public void setSizeFile(String sizeFile) {
        this.sizeFile = sizeFile;
    }

    public String getDateCreateFile() {
        return dateCreateFile;
    }

    public void setDateCreateFile(String dateCreateFile) {
        this.dateCreateFile = dateCreateFile;
    }

    public String getDateUpdateFile() {
        return dateUpdateFile;
    }

    public void setDateUpdateFile(String dateUpdateFile) {
        this.dateUpdateFile = dateUpdateFile;
    }
}
