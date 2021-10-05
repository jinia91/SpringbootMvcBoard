package com.project.board.board.controller;

import com.project.board.board.domain.Article;
import com.project.board.board.handler.PagingBoxHandler;
import com.project.board.board.handler.PagingHandler;
import com.project.board.board.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardServiceImpl boardService;

    @GetMapping("/board/list")
    public String getBoardList(@RequestParam(required = false) Integer page,
                               @RequestParam(required = false) Integer listNum,
                               PagingHandler pagingHandler, PagingBoxHandler pagingBoxHandler, Model model){


        pagingHandler.setCurPage(page);
        pagingHandler.setArticleCntInAPage(listNum);

        List<Article> articleList = boardService.getBoardList(pagingHandler);
        Map<String, Integer> articleTotalCnt = boardService.getArticleTotalCnt();

        pagingBoxHandler.buildPagingBox(pagingHandler, articleTotalCnt.get("totalCnt"));

        model.addAttribute("articles",articleList);
        model.addAttribute("pageTool", pagingBoxHandler);
        return "board/list";
    }


}
