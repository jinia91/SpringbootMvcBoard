package com.project.board.user.repository;

import com.project.board.user.domain.User;


public interface UserRepository {

    void saveUser(User user);
    User findUserById(String id);
    boolean existByUserId(String userId);
    boolean existByEmail(String email);
    User findUserByEmail(String email);
    void updatePwd(User user);
}
