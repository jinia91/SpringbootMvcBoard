package com.project.board.user.service;

import com.project.board.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("회원가입 DB 저장 성공 테스트")
    public void userJoinTestV1() throws Exception {
        // given
        User user = getTestUser();
        // when
        String joinUserId = userService.join(user);
        User findUser = userService.findUser(joinUserId);
        // then
        assertThat(user.getUserId()).isEqualTo(findUser.getUserId());
    }


    private User getTestUser() {
        User user = new User();
        user.setUserId("프붕");
        user.setUserPwd("1234");
        user.setUserName("홍길동");
        user.setEmail("sdf@sdf");
        user.setBirthDate(LocalDate.now());
        user.setCreateDate(LocalDateTime.now());
        return user;
    }


}