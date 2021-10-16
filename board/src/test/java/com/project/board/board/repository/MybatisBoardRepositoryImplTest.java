package com.project.board.board.repository;

import com.project.board.board.domain.Article;
import com.project.board.board.handler.PagingHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MybatisBoardRepositoryImplTest {

    @Autowired
    MybatisBoardRepositoryImpl mybatisBoardRepository;


//    @Test
//    public void dummyDataInsert() throws Exception {
//    // given
//        for(int i =0; i<132; i++){
//            Article article = new Article();
//            article.setTitle("더미데이터"+ RandomString.make(8));
//            article.setContents(RandomString.make(8));
//            article.setHit(1);
//            article.setWriterId("testId1");
//
//            mybatisBoardRepository.writeArticle(article);
//        }

    // when

    // then
//    }

    @Test
    @DisplayName("페이징 테스트V1")
    public void pagingTest() throws Exception {
    // given
        PagingHandler pi = new PagingHandler();

    // when
        List<Article> list = mybatisBoardRepository.getList(pi);

        // then
        for (Article article : list) {
            System.out.println("articleUid = " + article.getArticleUid());
        }
        Assertions.assertThat(list.size()).isEqualTo(10);

    }

    @Test
    @DisplayName("검색쿼리 작동 성공 테스트V1")
    public void pagingWithSearchingTest() throws Exception {
    // given
        PagingHandler pi = new PagingHandler();

        pi.setSearchType("title");
        pi.setSearchKeyword("네트워크");

        System.out.println("pi.getSearchKeyword() = " + pi.getSearchKeyword());
        System.out.println("pi.getSearchKeyword() = " + pi.getSearchType());

    // when
        List<Article> list = mybatisBoardRepository.getListWithSearchCriteria(pi);

        // then
        for (Article article : list) {
            System.out.println("articleUid = " + article.getArticleUid());
        }

        System.out.println(list.size());

    }

//    @Test
//    @DisplayName("페이징 테스트V2")
//    public void pagingTestV2() throws Exception {
//        // given
//        PagingHandler pi = new PagingHandler();
//        pi.setOffSet(2600280);
//
//        // when
//        List<Article> list = mybatisBoardRepository.getList(pi);
//
//        // then
//        for (Article article : list) {
//            System.out.println("articleUid = " + article.getArticleUid());
//        }
//        Assertions.assertThat(list.size()).isEqualTo(10);
//
//    }



}