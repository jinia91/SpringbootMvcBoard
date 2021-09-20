package com.project.board.user.service;

import com.project.board.user.dto.EmailAddress;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailToUserServiceImplTest {

    @Autowired
    EmailToUserServiceImpl mailToUserService;

    @Test
    @DisplayName("이메일 실제 전송 테스트")
    public void sendEmailTest() throws Exception {
    // given
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setEmail("q1q2q0@naver.com");

    // when
        mailToUserService.sendEmailForJoinAuth(emailAddress.getEmail());
    // then

    }

}