package com.project.board.user.service;

import com.project.board.user.dto.EmailAddress;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MailToUserServiceImplTest {

    @Autowired
    EmailToUserServiceImpl mailToUserService;

    @Test
    @DisplayName("이메일 실제 전송 테스트")
    public void sendEmailTest() throws Exception {
    // given
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setEmail("prjstudy91@gmail.com");

    // when
        mailToUserService.sendEmailForJoinAuth(emailAddress.getEmail());
    // then

    }

    @Test
    @DisplayName("임시 비밀번호 메일전송 테스트")
    public void sendEmailWithTmpPwdTest() throws Exception {
        // given
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setEmail("prjstudy91@gmail.com");
        String tmpPwd = "1234";

        // when
        mailToUserService.sendEmailWithTmpPwd(emailAddress.getEmail(),"1234");
        // then
    }

}