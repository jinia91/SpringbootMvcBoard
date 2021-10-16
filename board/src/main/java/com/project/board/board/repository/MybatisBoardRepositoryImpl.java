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

    @Select("select * from boardA where articleUid = #{articleId}")
    Article getArticle(int articleId);

    @Select("select totalCnt from boardATotalCnt")
    Map<String, Integer> getArticleTotalCnt();

    @Update("Update BoardA " +
            "set title = #{title}, contents = #{contents} " +
            "where articleUid = #{articleUid}")
    void updateArticle(Article article);

    @Delete("Delete  " +
            "From BoardA " +
            "where articleUid = #{articleId}")
    void deleteArticle(int articleId);



    @Update("Update BoardA " +
            "set hit = hit+1 " +
            "where articleUid = #{articleId}")
    void updateHit(int articleId);



    @Select("select b.articleUid, b.contents, b.title, b.writerId, b.writtenDate, b.hit " +
            "from ( " +
            "select articleUid " +
            "from boardA " +
            "where ${searchType} like #{searchKeyword} " +
            "order by articleUid desc " +
            "limit #{articleCntInAPage} offset #{offSet}) a  " +
            "join boardA b on (a.articleUid = b.articleUid) " +
            "order by articleUid desc")
    List<Article> getListWithSearchCriteria(PagingHandler pagingHandler);

    @Select("select count(articleUid) as cnt " +
            "from boardA " +
            "where ${searchType} like #{searchKeyword} " +
            "order by articleUid desc")
    Map<String, Integer> getSearchedArticleCnt(PagingHandler pagingHandler);

}
