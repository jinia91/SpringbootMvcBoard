package com.project.board.file.domain;

import lombok.Data;

@Data
public class UploadedImg {
    private String uploadFileName;
    private String storeFileName;

    public UploadedImg(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
