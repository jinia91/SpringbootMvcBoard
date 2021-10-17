package com.project.board.reply.repository;

import com.project.board.reply.domain.Reply;

import java.util.List;

public interface ReplyRepository {

    void writeReply(Reply reply);
    List<Reply> getReplys(int articleUid);
}
