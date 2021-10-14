package com.project.board.board.repository;


import com.project.board.board.domain.Article;
import com.project.board.board.handler.PagingHandler;

import java.util.List;
import java.util.Map;

public interface BoardRepository {

    List<Article> getList(PagingHandler pagingHandler);
    void writeArticle(Article article);
    Map<String, Integer> getArticleTotalCnt();
    Article getArticle(int articleId);
    void updateArticle(Article article);
}
