package com.project.board.reply.service;

import com.project.board.reply.domain.Reply;
import com.project.board.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl {

    private final ReplyRepository replyRepository;

    public void postNewReply(Reply reply){
        replyRepository.writeReply(reply);
    }

    public List<Reply> getReplyList(int articleUid){

        return replyRepository.getReplys(articleUid);
    }



}
