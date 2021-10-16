package com.project.board.board.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ArticleDto {

    private int articleUid;
    private String writerId;
    private String title;
    private String contents;
    private LocalDate writtenDate;
    private int hit;

}
