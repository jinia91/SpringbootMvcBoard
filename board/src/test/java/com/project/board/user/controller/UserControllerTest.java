package com.project.board.user.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("회원가입폼으로 포워딩 테스트")
    public void joinFormForwarding() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(get("/join"));
        // then
        perform.andExpect(view().name("user/form/join"))
                .andExpect(model().attributeExists("joinFormDto"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 처리 통합 검증 성공 테스트")
    public void joinFormValidationTest() throws Exception {
        // given
        // when
        ResultActions perform = getTestUserWithMoc();
        // then
        perform.andExpect(view().name("redirect:/joinSuc"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("회원가입 처리 통합 검증 실패 테스트V1")
    public void joinFormValidationFailTestV1() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(post("/join")
                .param("userId", "testId1")
                .param("userPwd", "1q2w3e4r!")
                .param("userName", "김프붕")
                .param("birthDate", "2000-09-10")
                .param("email", "qwer@qwer").with(csrf()));

        ResultActions perform2 = mockMvc.perform(post("/join")
                .param("userId", "testId1")         // 아이디 중복 테스트
                .param("userPwd", "1q2w3e4r!")
                .param("userName", "김프붕")
                .param("birthDate", "2000-09-10")
                .param("email", "qwer@qwer2").with(csrf()));

        // then
        perform2.andExpect(view().name("user/form/join")); // 중복 아이디 검증
    }

    @Test
    @DisplayName("회원가입 처리 통합 검증 실패 테스트V2")
    public void joinFormValidationFailTestV2() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(post("/join")
                .param("userId", "testId1")
                .param("userPwd", "1q2w3e4r!")
                .param("userName", "김프붕")
                .param("birthDate", "2000-09-10")
                .param("email", "qwer@qwer").with(csrf()));

        ResultActions perform2 = mockMvc.perform(post("/join")
                .param("userId", "testId2")
                .param("userPwd", "1q2w3e4r!")
                .param("userName", "김프붕")
                .param("birthDate", "2000-09-10")
                .param("email", "qwer@qwer").with(csrf()));  // 메일 중복

        // then
        perform2.andExpect(view().name("user/form/join")); // 중복 메일 검증
    }
    @Test
    @DisplayName("아이디 찾기 성공 테스트")
    public void findIdTestSuc() throws Exception {
        // given
        ResultActions perform = getTestUserWithMoc();
        // when
        ResultActions perform2 = mockMvc.perform(post("/user/help/idInquiry")
                .param("email", "qwer@qwer")
                .param("userName", "김프붕").with(csrf()));

        // then
        perform2.andExpect(view().name("user/help/returnId"));
    }
    @Test
    @DisplayName("아이디 찾기 실패 테스트")
    public void findIdTestFail() throws Exception {
        // given
        ResultActions perform = getTestUserWithMoc();
        // when
        ResultActions perform2 = mockMvc.perform(post("/user/help/idInquiry")
                .param("email", "qwer@qwer")
                .param("userName", "김프으붕").with(csrf()));

        // then
        perform2.andExpect(view().name("/error"));
    }
    @Test
    @DisplayName("비밀번호 찾기 성공 테스트")
    public void findPwdTestSuc() throws Exception {
        // given
        ResultActions perform = getTestUserWithMoc();
        // when
        ResultActions perform2 = mockMvc.perform(post("/user/help/pwdInquiry")
                .param("email", "qwer@qwer")
                .param("userId", "testId100")
                .param("userName", "김프붕").with(csrf()));

        // then
        perform2.andExpect(view().name("user/help/returnPwd"));
    }
    @Test
    @DisplayName("비밀번호 찾기 실패 테스트")
    public void findPwdTestFail() throws Exception {
        // given
        ResultActions perform = getTestUserWithMoc();
        // when
        ResultActions perform2 = mockMvc.perform(post("/user/help/pwdInquiry")
                .param("email", "qwer@qwer")
                .param("userId", "testId1000")
                .param("userName", "김프으붕").with(csrf()));

        // then
        perform2.andExpect(view().name("/error"));
    }


    private ResultActions getTestUserWithMoc() throws Exception {
        return mockMvc.perform(post("/join")
                .param("userId", "testId100")
                .param("userPwd", "1q2w3e4r!")
                .param("userName", "김프붕")
                .param("birthDate", "2000-09-10")
                .param("email", "qwer@qwer").with(csrf()));
    }


}