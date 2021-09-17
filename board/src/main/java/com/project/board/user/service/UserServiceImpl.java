package com.project.board.user.service;

import com.project.board.user.domain.User;
import com.project.board.user.repository.UserRepository;
import com.project.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public String join(User user) {
    userRepository.saveUser(user);
    log.info("info Join log = {}",user);
    return user.getUserId();
    }

    @Override
    public User findUser(String userId) {
        User findUser = userRepository.findUserById(userId);
        log.info("info find log = {}",findUser);
        return findUser;
    }


}
