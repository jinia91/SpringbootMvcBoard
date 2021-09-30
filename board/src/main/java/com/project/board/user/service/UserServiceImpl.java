package com.project.board.user.service;

import com.project.board.user.domain.User;
import com.project.board.user.dto.UserContext;
import com.project.board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public String join(User user) {
        userRepository.saveUser(user);
        log.info("info Join log = {}", user);
        return user.getUserId();
    }

    @Override
    public User findUserByUserId(String userId) {
        User findUser = userRepository.findUserById(userId);
        return findUser;
    }

    @Override
    public User findUserByEmail(String email) {
        User findUser = userRepository.findUserByEmail(email);
        return findUser;
    }

    @Override
    public boolean chkEmailValidatedWithName(String email, String userName) {
        User foundUser = findUserByEmail(email);
        if(foundUser == null) return false;
        if(foundUser.getUserName().equals(userName)) return true;
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User loginUser = userRepository.findUserById(userId);

        if(loginUser == null){
            throw new UsernameNotFoundException("해당 유저가 없습니다");
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("Default"));

        UserContext userContext = new UserContext(loginUser, roles);

        return userContext;

    }
}
