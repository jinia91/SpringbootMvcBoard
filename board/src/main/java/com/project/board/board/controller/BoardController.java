package com.project.board.board.controller;

import com.project.board.board.domain.Article;
import com.project.board.board.dto.ArticleDto;
import com.project.board.board.handler.PagingBoxHandler;
import com.project.board.board.handler.PagingHandler;
import com.project.board.board.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardServiceImpl boardService;
    private final ModelMapper modelMapper;

    @GetMapping("/board/list")
    public String getBoardList(@RequestParam(required = false) Integer page,
                               @RequestParam(required = false) Integer listNum,
                               PagingHandler pagingHandler, PagingBoxHandler pagingBoxHandler, Model model){


        buildPagingTool(page, listNum, pagingHandler, pagingBoxHandler);
        List<Article> articleList = boardService.getBoardList(pagingHandler);

        model.addAttribute("articles",articleList);
        model.addAttribute("pageTool", pagingBoxHandler);
        return "board/list";
    }

    @GetMapping("/board/writeArticle")
    public String writeArticleForm(ArticleDto articleDto, Model model){
        model.addAttribute(articleDto);
        return "board/articleWriteForm";
    }

    @GetMapping("/board/editArticle/{articleId}")
    public String editArticleForm(@PathVariable int articleId, Model model){

        Article article = boardService.findArticle(articleId);
        ArticleDto articleDto = modelMapper.map(article, ArticleDto.class);
        model.addAttribute(articleDto);
        return "board/articleEditForm";
    }


    @PostMapping("/board/writeArticle")
    public String writeArticle(@ModelAttribute ArticleDto articleDto, Principal principal){

        String userId = principal.getName();
        Article article = modelMapper.map(articleDto, Article.class);
        article.setWriterId(userId);

        int articleId = boardService.postNewArticle(article);

        return "redirect:/board/" + articleId;
    }

    @GetMapping("/board/{articleId}")
    public String readArticle(@PathVariable int articleId, Model model){

        Article findArticle =boardService.findArticle(articleId);
        model.addAttribute(findArticle);
        return "board/articleView";
    }


    private void buildPagingTool(Integer page, Integer listNum, PagingHandler pagingHandler, PagingBoxHandler pagingBoxHandler) {
        pagingHandler.setCurPage(page);
        pagingHandler.setArticleCntInAPage(listNum);
        Map<String, Integer> articleTotalCnt = boardService.getArticleTotalCnt();
        pagingBoxHandler.buildPagingBox(pagingHandler, articleTotalCnt.get("totalCnt"));
    }


}
