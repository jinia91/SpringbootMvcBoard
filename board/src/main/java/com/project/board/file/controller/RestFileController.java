package com.project.board.file.controller;

import com.project.board.file.domain.UploadedImg;
import com.project.board.file.dto.UploadImgDto;
import com.project.board.file.repository.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RestFileController {

    @Value("${file.url}")
    private String fileUrl;

    @Value("${git.imgUrl}")
    private String imgUrl;

    final private FileStore fileStore;


    @PostMapping("/board/uploadImg")
    public @ResponseBody
    String imgUpload(@ModelAttribute UploadImgDto uploadImgDto) throws IOException {

        UploadedImg uploadImg = fileStore.storeFile(uploadImgDto.getImg());
        return imgUrl + uploadImg.getStoreFileName()+"?raw=true";
    }

}
