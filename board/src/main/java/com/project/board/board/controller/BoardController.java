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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
    public String editArticleForm(@PathVariable int articleId,Principal principal, Model model){

        Article article = boardService.findArticle(articleId);

        if(!principal.getName().equals(article.getWriterId())){
            throw new IllegalArgumentException("작성자와 로그인정보가 일치하지 않습니다");
        }

            ArticleDto articleDto = modelMapper.map(article, ArticleDto.class);
        model.addAttribute(articleDto);
        return "board/articleEditForm";
    }

    @PostMapping("/board/writeArticle")
    public String writeArticle(@ModelAttribute ArticleDto articleDto, Principal principal){

        String userId = principal.getName();
        articleDto.setWriterId(userId);
        Article article = modelMapper.map(articleDto, Article.class);

        int articleId = boardService.postNewArticle(article);


        return "redirect:/board/article/" + articleId;
    }

    @PostMapping("/board/editArticle/{articleId}")
    public String editArticle(@PathVariable int articleId, @ModelAttribute ArticleDto articleDto, Principal principal){


        Article article = modelMapper.map(articleDto, Article.class);
        article.setArticleUid(articleId);
        boardService.editArticle(article);
        return "redirect:/board/article/" + articleId;


    }

    @GetMapping("/board/article/{articleId}")
    public String readArticle(@PathVariable int articleId,
                              @CookieValue(required = false, name = "view") String cookie, HttpServletResponse response,
                              Principal principal, Model model){

        // 쿠키 사용 조회수 증가
        addHitWithCookie(articleId, cookie, response);

        Article findArticle =boardService.findArticle(articleId);

        if(principal == null||!principal.getName().equals(findArticle.getWriterId())){
            model.addAttribute("isItYou", false);
        }
        else{
            model.addAttribute("isItYou", true);
        }
        model.addAttribute(findArticle);
        return "board/articleView";
    }

    @GetMapping("/board/deleteArticle/{articleId}")
    public String deleteArticleForm(@PathVariable int articleId, Principal principal, Model model){

        Article article = boardService.findArticle(articleId);
        if(!principal.getName().equals(article.getWriterId())){
            throw new IllegalArgumentException("작성자와 로그인정보가 일치하지 않습니다");
        }

        model.addAttribute("articleId",articleId);

        return "/board/deleteArticleConfirmForm";

    }

    @PostMapping("/board/deleteArticle/{articleId}")
    public String deleteArticle(@PathVariable int articleId, Principal principal){

        boardService.deleteArticle(articleId);

        return "redirect:/board/list";


    }


    private void buildPagingTool(Integer page, Integer listNum, PagingHandler pagingHandler, PagingBoxHandler pagingBoxHandler) {
        pagingHandler.setCurPage(page);
        pagingHandler.setArticleCntInAPage(listNum);
        Map<String, Integer> articleTotalCnt = boardService.getArticleTotalCnt();
        pagingBoxHandler.buildPagingBox(pagingHandler, articleTotalCnt.get("totalCnt"));
    }

    private void addHitWithCookie(int articleId, String cookie, HttpServletResponse response) {
        if(cookie == null) {
            Cookie viewCookie = new Cookie("view", articleId +"/");
            viewCookie.setComment("게시물 조회 확인용");
            viewCookie.setMaxAge(60 * 60);
            boardService.addHit(articleId);
            response.addCookie(viewCookie);
        }

        else{
            boolean isRead =false;
            String[] viewCookieList = cookie.split("/");
            for (String alreadyRead : viewCookieList) {
                if(alreadyRead.equals(String.valueOf(articleId))){
                    isRead = true;
                    break;
                };
            }
            if(!isRead){
                cookie += articleId +"/";
                boardService.addHit(articleId);
            }
            response.addCookie(new Cookie("view", cookie));
        }
    }


}
