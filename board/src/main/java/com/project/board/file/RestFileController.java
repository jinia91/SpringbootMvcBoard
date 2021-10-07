package com.project.board.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RestFileController {

    @Value("${file.url}")
    private String fileUrl;
    final private FileStore fileStore;

    @PostMapping("/board/uploadImg")
    public @ResponseBody
    String sendEmailForJoin(@ModelAttribute UploadImgDto uploadImgDto) throws IOException {

        UploadedImg uploadImg = fileStore.storeFile(uploadImgDto.getImg());
        return fileUrl + uploadImg.getStoreFileName();
    }

    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }


}
