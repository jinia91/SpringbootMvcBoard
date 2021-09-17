package com.project.board.user.service;

import com.project.board.user.domain.User;

public interface UserService {

    String join(User user);
    User findUser(String userId);

}
