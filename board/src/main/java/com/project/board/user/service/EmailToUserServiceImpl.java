package com.project.board.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailToUserServiceImpl {

    private final JavaMailSender javaMailSender;

    public String sendEmailForJoinAuth(String emailAddress) {

        String authNum = getAuthNum();
        log.info("인증번호 ={}", authNum);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject("프로젝트 회원 가입 인증");
        mailMessage.setText("인증번호는 " + authNum + "입니다.");

        log.info("보내는 메일주소 ={}", emailAddress);
        javaMailSender.send(mailMessage);

        return authNum;
    }

    private String getAuthNum() {
        return ((int) (Math.random() * 100000))+"";
    }
}
