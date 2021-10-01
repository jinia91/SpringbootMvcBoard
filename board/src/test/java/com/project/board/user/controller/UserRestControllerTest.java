package com.project.board.user.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("회원가입 메일인증 REST API 테스트")
    public void sendForJoinAuthTest() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(post("/join/sendEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"asdf@asdf\"}").with(csrf()));

        // then
        perform
                .andExpect(status().isOk());
    }



    @Test
    @DisplayName("아이디찾기 REST API P/F 테스트")
    public void sendEmailForFindIdTest() throws Exception {
        // given
        ResultActions perform1 = mockMvc.perform(post("/join")
                .param("userId", "testId100")
                .param("userPwd", "1q2w3e4r!")
                .param("userName", "김프붕")
                .param("birthDate", "2000-09-10")
                .param("email", "qwer@qwer").with(csrf()));

        // when
        ResultActions perform2 = mockMvc.perform(post("/user/help/sendEmailWithName")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"김프붕\", \"email\":\"qwer@qwer\"}").with(csrf()));

        ResultActions perform3 = mockMvc.perform(post("/user/help/sendEmailWithName")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"김프붕이\", \"email\":\"qwer@qwer\"}").with(csrf()));

        // then
        perform2
                .andExpect(status().isAccepted());
        perform3
                .andExpect(status().is4xxClientError());

    }

    @Test
    @DisplayName("비밀번호찾기 REST API P/F 테스트")
    public void sendEmailForFindPwdTest() throws Exception {
        // given
        ResultActions perform1 = mockMvc.perform(post("/join")
                .param("userId", "testId100")
                .param("userPwd", "1q2w3e4r!")
                .param("userName", "김프붕")
                .param("birthDate", "2000-09-10")
                .param("email", "qwer@qwer").with(csrf()));

        // when
        ResultActions perform2 = mockMvc.perform(post("/user/help/sendEmailWithNameAndId")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"testId100\", \"userName\":\"김프붕\", \"email\":\"qwer@qwer\"}").with(csrf()));

        ResultActions perform3 = mockMvc.perform(post("/user/help/sendEmailWithNameAndId")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"testId1000\",\"userName\":\"김프붕이\", \"email\":\"qwer@qwer\"}").with(csrf()));

        // then
        perform2
                .andExpect(status().isAccepted());
        perform3
                .andExpect(status().is4xxClientError());

    }

}