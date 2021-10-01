package com.project.board.user.service;

import com.project.board.user.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    String join(User user);
    User findUserByUserId(String userId);
    User findUserByEmail(String email);
    boolean chkEmailValidatedWithName(String email, String userName);
    boolean chkEmailValidatedWithNameAndId(String email, String userId, String userName);
    String changeUserPwdToTmp(User user);
}
