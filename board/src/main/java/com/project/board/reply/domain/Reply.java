package com.project.board.reply.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reply {

    private int articleUid;
    private int replyUid;
    private String replyWriterId;
    private String replyContents;
    private LocalDateTime replyWrittenDate;

}
