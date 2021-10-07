package com.project.board.file;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadImgDto {

    private MultipartFile img;


}

