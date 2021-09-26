package com.project.board.user.service;

import com.project.board.user.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    String join(User user);
    User findUser(String userId);

}
