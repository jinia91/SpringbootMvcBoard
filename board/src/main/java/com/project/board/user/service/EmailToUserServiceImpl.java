package com.project.board.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailToUserServiceImpl {

    private final JavaMailSender javaMailSender;

    // 가입인증
    public String sendEmailForJoinAuth(String emailAddress) {

        String authNum = getAuthNum(8);
        log.info("인증번호 ={}", authNum);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject("프로젝트 회원 가입 인증");
        mailMessage.setText("인증번호는 " + authNum + "입니다.");

        log.info("보내는 메일주소 ={}", emailAddress);
        log.info("인증번호 ={}", authNum);
//        javaMailSender.send(mailMessage);

        return authNum;
    }

    // 임시비밀번호
    public void sendEmailWithTmpPwd(String email, String pwd) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("프로젝트 회원 가입 임시 비밀번호");
        mailMessage.setText("임시 비밀번호는 " + pwd + "입니다.");

        log.info("보내는 메일주소 ={}", email);
        log.info("임시 비번 ={}", pwd);
//        javaMailSender.send(mailMessage);
    }


    private String getAuthNum(int len) {

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        for(int i = 0; i<len; i++){
                sb.append(sr.nextInt(10));
        }
        return sb.toString();
    }
}
