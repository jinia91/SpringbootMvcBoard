package com.project.board.board.controller;

import com.project.board.board.domain.Article;
import com.project.board.board.dto.ArticleDto;
import com.project.board.board.handler.PagingBoxHandler;
import com.project.board.board.handler.PagingHandler;
import com.project.board.board.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class withGitController {

    private final BoardServiceImpl boardService;
    private final ModelMapper modelMapper;
    @Value("${git.repo}")
    private String gitRepo;
    @Value("${git.gitToken}")
    private String gitToken;



    @PostMapping("/board/writeArticleWithGit")
    public String writeArticleWithGit(@ModelAttribute ArticleDto articleDto, Principal principal) throws IOException {

        String userId = principal.getName();
        articleDto.setWriterId(userId);
        Article article = modelMapper.map(articleDto, Article.class);

        int articleId = boardService.postNewArticle(article);

        GitHub gitHub = new GitHubBuilder().withOAuthToken(gitToken).build();
        GHRepository repository = gitHub.getRepository(gitRepo);
        repository.createContent().path(article.getTitle()+".md").content(article.getContents()).message("test").branch("main").commit();



        return "redirect:/board/article/" + articleId;
    }


}
