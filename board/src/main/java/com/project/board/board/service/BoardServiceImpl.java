package com.project.board.board.service;

import com.project.board.board.domain.Article;
import com.project.board.board.handler.PagingHandler;
import com.project.board.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl {

    private final BoardRepository boardRepository;

    public List<Article> getBoardList(PagingHandler pagingHandler){
       return boardRepository.getList(pagingHandler);
    }

    public Map<String, Integer> getArticleTotalCnt(){
        return boardRepository.getArticleTotalCnt();
    }
}
