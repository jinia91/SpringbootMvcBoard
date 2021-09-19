package com.project.board.user.service;

import com.project.board.user.domain.User;
import com.project.board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String join(User user) {
        pwdEncoding(user);
        userRepository.saveUser(user);
        log.info("info Join log = {}", user);
        return user.getUserId();
    }

    @Override
    public User findUser(String userId) {
        User findUser = userRepository.findUserById(userId);
        log.info("info find log = {}", findUser);
        return findUser;
    }

    private void pwdEncoding(User user) {
        user.setUserPwd(passwordEncoder.encode(user.getUserPwd()));
    }

}
