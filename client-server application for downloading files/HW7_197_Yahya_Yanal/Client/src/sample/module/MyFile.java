package sample.module;

import java.io.Serializable;

public class MyFile implements Serializable {
    private final String fileName;
    private final double fileSize;


    public MyFile(String fileName, double fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public double getFileSize() {
        return fileSize;
    }
}
