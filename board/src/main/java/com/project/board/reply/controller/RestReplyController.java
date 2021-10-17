package com.project.board.reply.controller;

import com.project.board.reply.domain.Reply;
import com.project.board.reply.dto.ReplyDto;
import com.project.board.reply.service.ReplyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/reply/list/{articleUid}")
    public @ResponseBody
    List<Reply> getReplyList(@PathVariable int articleUid) {

        return replyService.getReplyList(articleUid);
    }


    private void buildReply(ReplyDto replyDto, Reply reply, int articleUid, Principal principal) {
        reply.setReplyWriterId(principal.getName());
        reply.setReplyContents(replyDto.getContents());
        reply.setArticleUid(articleUid);
    }


}
