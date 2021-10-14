package com.project.board.board.repository;

import com.project.board.board.domain.Article;
import com.project.board.board.handler.PagingHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MybatisBoardRepositoryImpl extends BoardRepository {

    @Select("select b.articleUid, b.contents, b.title, b.writerId, b.writtenDate, b.hit " +
            "from ( " +
            "select articleUid " +
            "from boardA " +
            "order by articleUid desc " +
            "limit #{articleCntInAPage} offset #{offSet}) a  " +
            "join boardA b on (a.articleUid = b.articleUid) " +
            "order by articleUid desc")
    List<Article> getList(PagingHandler pagingHandler);

    @Insert("Insert into boardA" +
                "(title, writerId,contents)" +
            "values" +
                "(#{title},#{writerId},#{contents})")
    @Options(useGeneratedKeys = true, keyProperty = "articleUid")
    void writeArticle(Article article);

    @Select("select * from boardA where articleUid = #{articleUid}")
    Article getArticle(int articleId);

    @Select("select totalCnt from boardATotalCnt")
    Map<String, Integer> getArticleTotalCnt();

    @Update("Update BoardA " +
            "set (title, contents) = (#{title},#{contents}) " +
            "where articleUid = #{articleUid}")
    void updateArticle(Article article);



}
