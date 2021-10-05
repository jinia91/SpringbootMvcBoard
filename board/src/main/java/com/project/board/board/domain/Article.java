package com.project.board.board.domain;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Article {

    private int articleUid;
    private String title;
    private String writerId;
    private String contents;
    private LocalDate writtenDate;
    private int hit;

}
