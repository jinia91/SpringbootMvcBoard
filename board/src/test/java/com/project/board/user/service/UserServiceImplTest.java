package com.project.board.user.service;

import com.project.board.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 DB 저장 성공 테스트")
    public void userJoinTestV1() throws Exception {
        // given
        User user = getTestUser();
        // when
        String joinUserId = userService.join(user);
        User findUser = userService.findUserByUserId(joinUserId);
        // then
        assertThat(user.getUserId()).isEqualTo(findUser.getUserId());
        // 암호화
        assertThat(findUser.getUserPwd()).isNotEqualTo("1234");
        assertThat(passwordEncoder.matches("1234",findUser.getUserPwd())).isEqualTo(true);
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

    @Test
    @DisplayName("email로 유저 조회 테스트")
    void findUserByEmail(){
        //given
        User user = getTestUser();
        String joinUserId = userService.join(user);

        //when
        User foundUser1 = userService.findUserByUserId(user.getUserId());
        User foundUser2 = userService.findUserByEmail(user.getEmail());
        //then
        assertThat(foundUser1.getEmail()).isEqualTo(foundUser1.getEmail());
        assertThat(foundUser2.getEmail()).isEqualTo(foundUser2.getEmail());
    }

    @Test
    @DisplayName("검색 아이디 조건 일치 테스트")
    void foundUserMatchingTest(){
        //given
        User user = getTestUser();
        String joinUserId = userService.join(user);

        //when
        User foundUser = userService.findUserByEmail(user.getEmail());
        boolean rs1 = userService.chkEmailValidatedWithName(user.getEmail(), user.getUserName());
        boolean rs2 = userService.chkEmailValidatedWithNameAndId(user.getEmail(), user.getUserId(), user.getUserName());
        boolean rs3 = userService.chkEmailValidatedWithNameAndId(user.getEmail(), "다른아이디", "다른 이름");
        //then
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(rs1).isEqualTo(true);
        assertThat(rs2).isEqualTo(true);
        assertThat(rs3).isEqualTo(false);
    }

    @Test
    @DisplayName("유저 비밀번호 난수비밀번호로 변경 테스트")
    public void changeUserPwdToTmpTest() throws Exception {
    // given
        User user = getTestUser();
        userService.join(user);
        User foundUser = userService.findUserByUserId(user.getUserId());
        String beforePwd = foundUser.getUserPwd();
        // when
        String afterPwd = userService.changeUserPwdToTmp(foundUser);
        userService.findUserByUserId(user.getUserId());
        // then
        assertThat(beforePwd).isNotEqualTo(foundUser.getUserPwd());
        assertThat(passwordEncoder.matches(afterPwd, foundUser.getUserPwd())).isEqualTo(true);
    }


}