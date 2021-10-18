package com.project.board.reply.controller;

import com.project.board.reply.domain.Reply;
import com.project.board.reply.dto.ReplyDto;
import com.project.board.reply.service.ReplyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestReplyController {

    private final ReplyServiceImpl replyService;
    private final ModelMapper modelMapper;

    @PostMapping("/reply/writeReply/{articleUid}")
    public @ResponseBody
    List<Reply> writeReply(@RequestBody ReplyDto replyDto, Reply reply,
                      @PathVariable int articleUid,
                      Principal principal) {

        buildReply(replyDto, reply, articleUid, principal);
        replyService.postNewReply(reply);
    return replyService.getReplyList(articleUid);
    }

    @PostMapping("/reply/deleteReply/{articleUid}/{replyUid}")
    public @ResponseBody
    List<Reply> deleteReply(@PathVariable int articleUid,
                            @PathVariable int replyUid, Principal principal) {

        Reply reply = replyService.getReply(replyUid);
        if(principal ==null||!principal.getName().equals(reply.getReplyWriterId())){
            throw new IllegalArgumentException("작성자와 로그인정보가 일치하지 않습니다");
        }

        replyService.deleteReply(replyUid);

        return replyService.getReplyList(articleUid);
    }


    @GetMapping("/reply/list/{articleUid}")
    public @ResponseBody
    List<Reply> getReplyList(@PathVariable int articleUid) {

        List<Reply> replyList = replyService.getReplyList(articleUid);

        for (Reply reply : replyList) {
            System.out.println("reply.getReplyWrittenDate() = " + reply.getReplyWrittenDate());
        }


        return replyList;
    }


    private void buildReply(ReplyDto replyDto, Reply reply, int articleUid, Principal principal) {
        reply.setReplyWriterId(principal.getName());
        reply.setReplyContents(replyDto.getContents());
        reply.setArticleUid(articleUid);
    }


}
