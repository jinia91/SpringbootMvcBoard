package com.project.board.board.repository;

import com.project.board.board.domain.Article;
import com.project.board.board.handler.PagingHandler;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface MybatisBoardRepositoryImpl extends BoardRepository {

    @Select("select b.articleUid, b.contents, b.title, b.writerId, b.writtenDate, b.hit " +
            "from ( " +
            "select articleUid " +
            "from board " +
            "order by articleUid desc " +
            "limit #{articleCntInAPage} offset #{offSet}) a " +
            "join board b on (a.articleUid = b.articleUid)")
    List<Article> getList(PagingHandler pagingHandler);

    @Insert("Insert into board" +
                "(title, writerId,contents, hit)" +
            "values" +
                "(#{title},#{writerId},#{contents},#{hit})")
    void writeArticle(Article article);

    @Select("select totalCnt from boardTotalCnt")
    Map<String, Integer> getArticleTotalCnt();

}
