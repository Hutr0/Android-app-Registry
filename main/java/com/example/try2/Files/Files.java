package com.example.try2.Files;

public class Files {

    private String file;
    private String fileTop;

    public Files(String fileTop, String file) {

        this.fileTop = fileTop;
        this.file = file;
    }

    public String getFiles() { return this.file; }

    public String getFilesTop() { return this.fileTop; }

}