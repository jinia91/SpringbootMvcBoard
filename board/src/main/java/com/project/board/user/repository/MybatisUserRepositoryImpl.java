package com.project.board.user.repository;

import com.project.board.user.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MybatisUserRepositoryImpl extends UserRepository {

    @Insert("Insert into User(userId, userPwd, userName, email, birthDate, createDate) " +
            "values(#{userId},#{userPwd},#{userName},#{email},#{birthDate}, current_timestamp)")
    @Options(useGeneratedKeys = true,keyProperty ="userUid" )
    void saveUser(User user);

    @Select("Select Exists (Select * From User where userId= #{userId}) as isExists")
    boolean existByUserId(String userId);

    @Select("Select Exists (Select * From User where email= #{email}) as isExists")
    boolean existByEmail(String email);

    @Select("Select * From User where email= #{email}")
    User findUserByEmail(String email);

    @Select("Select * From User where userId= #{userId}")
    User findUserById(String userId);

    @Update("Update User set userPwd = #{userPwd} where userId = #{userId}")
    void updatePwd(User user);

    @Update("Update User set bio = #{bio}, userName =#{userName} where userId = #{userId}")
    void updateUserBioAndName(User user);
}
