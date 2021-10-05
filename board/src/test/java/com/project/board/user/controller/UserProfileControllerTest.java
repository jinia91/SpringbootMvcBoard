package com.project.board.user.controller;

import com.project.board.user.dto.UserProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("회원프로필 리소스 접근 인가 실패 테스트")
    @WithAnonymousUser
    public void forwardingBlockToProfileFailTest() throws Exception {
    // given
        ResultActions perform = mockMvc.perform(post("/join")
                .param("userId", "testId1")
                .param("userPwd", "1q2w3e4r!")
                .param("userName", "김프붕")
                .param("birthDate", "2000-09-10")
                .param("email", "qwer@qwer").with(csrf()));

        // when
        ResultActions perform2 = mockMvc.perform(get("/profile/testId1"));


    // then
        perform2.andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @DisplayName("회원프로필 리소스 접근 인가 성공 테스트")
    @WithMockUser
    public void forwardingBlockToProfileSucTest() throws Exception {
        // given
        ResultActions perform = mockMvc.perform(post("/join")
                .param("userId", "testId1")
                .param("userPwd", "1q2w3e4r!")
                .param("userName", "김프붕")
                .param("birthDate", "2000-09-10")
                .param("email", "qwer@qwer").with(csrf()));

        // when
        ResultActions perform2 = mockMvc.perform(get("/profile/testId1"));


        // then
        perform2.andExpect(view().name("user/profile"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("회원프로필수정 비인가 테스트")
    @WithMockUser
    public void editProfileUnequalUserTest() throws Exception {
        // given
        ResultActions perform = mockMvc.perform(post("/join")
                .param("userId", "testId1")
                .param("userPwd", "1q2w3e4r!")
                .param("userName", "김프붕")
                .param("birthDate", "2000-09-10")
                .param("email", "qwer@qwer").with(csrf()));

        // when
        ResultActions perform2 = mockMvc.perform(get("/profile/testId1/edit"));

        // then
        perform2.andExpect(view().name("/error"));
    }

    @Test
    @DisplayName("회원프로필수정 인가 테스트")
    @WithMockUser(username = "testId1")
    public void editProfileEqualUserTest() throws Exception {
        // given
        ResultActions perform = mockMvc.perform(post("/join")
                .param("userId", "testId1")
                .param("userPwd", "1q2w3e4r!")
                .param("userName", "김프붕")
                .param("birthDate", "2000-09-10")
                .param("email", "qwer@qwer").with(csrf()));

        // when
        ResultActions perform2 = mockMvc.perform(get("/profile/testId1/edit"));

        // then
        perform2.andExpect(view().name("/user/form/userProfileEditForm"));
    }

    @Test
    @DisplayName("회원프로필수정 성공 테스트")
    @WithMockUser(username = "testId1")
    public void editProfileSucTest() throws Exception {
        // given
        ResultActions perform = mockMvc.perform(post("/join")
                .param("userId", "testId1")
                .param("userPwd", "1q2w3e4r!")
                .param("userName", "김프붕")
                .param("birthDate", "2000-09-10")
                .param("email", "qwer@qwer").with(csrf()));



        // when
        ResultActions perform2 = mockMvc.perform(post("/profile/testId1/edit")
                .param("bio","안녕")
                .param("userName", "이프붕")
                .with(csrf()));

        UserProfile user = new UserProfile();
        user.setUserId("testId1");
        user.setUserName("이프붕");
        user.setBio("안녕");
        user.setBirthDate(LocalDate.of(2000,9,10));
        user.setEmail("qwer@qwer");

        // then
        perform2.andExpect(redirectedUrl("/profile/testId1"));
        mockMvc.perform(get("/profile/testId1"))
                .andExpect(model().attribute("user", user));
    }


    @Test
    @DisplayName("회원비밀번호 변경 성공 테스트")
    @WithMockUser(username = "testId1")
    public void editPwdSucTest() throws Exception {
        // given
        ResultActions perform = mockMvc.perform(post("/join")
                .param("userId", "testId1")
                .param("userPwd", "1q2w3e4r!")
                .param("userName", "김프붕")
                .param("birthDate", "2000-09-10")
                .param("email", "qwer@qwer").with(csrf()));



        // when
        ResultActions perform2 = mockMvc.perform(post("/profile/testId1/edit/pwd")
                .param("beforePwd","1q2w3e4r!")
                .param("userPwd", "q1q2q3q4!")
                .with(csrf()));

        // then
        perform2.andExpect(redirectedUrl("/profile/testId1/edit/pwdSuc"));
    }

}