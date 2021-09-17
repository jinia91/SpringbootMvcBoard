package com.project.board.user.repository;

import com.project.board.user.domain.User;


public interface UserRepository {

    void saveUser(User user);
    User findUserById(String id);

}
