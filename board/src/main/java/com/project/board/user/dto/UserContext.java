package com.project.board.user.dto;

import com.project.board.user.domain.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserContext extends org.springframework.security.core.userdetails.User {

    public UserContext(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUserId(), user.getUserPwd(), authorities);
    }

}
