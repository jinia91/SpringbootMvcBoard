package com.project.board.reply.repository;

import com.project.board.reply.domain.Reply;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper

public interface ReplyMybatisRepository extends ReplyRepository {


    @Insert("Insert into Reply " +
            "(articleUid, replyWriterId, replyContents)" +
            "values " +
            "(#{articleUid}, #{replyWriterId}, #{replyContents})")
    @Options(useGeneratedKeys = true, keyProperty = "replyUid")
    void writeReply(Reply reply);


    @Select("Select * " +
            "from reply " +
            "where articleUid = #{articleUid} " +
            "order by replyUid")
    List<Reply> getReplys(int articleUid);

    @Select("Select * " +
            "from reply " +
            "where replyUid = #{replyUid} ")
    Reply getReply(int replyUid);

    @Delete("Delete " +
            "from reply " +
            "where replyUid = #{replyUid} ")
    void deleteReply(int replyUid);
}
