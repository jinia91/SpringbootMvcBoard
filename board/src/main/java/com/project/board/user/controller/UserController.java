package com.project.board.user.controller;

import com.project.board.user.domain.User;
import com.project.board.user.dto.EmailWithName;
import com.project.board.user.dto.EmailWithNameAndId;
import com.project.board.user.dto.JoinFormDto;
import com.project.board.user.service.EmailToUserServiceImpl;
import com.project.board.user.service.UserService;
import com.project.board.user.validation.JoinFormDtoValidator;
import com.project.board.user.validation.ValidationSequenceGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JoinFormDtoValidator joinFormDtoValidator;
    private final EmailToUserServiceImpl emailToUserService;

    @InitBinder("joinFormDto")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(joinFormDtoValidator);
    }

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute(new JoinFormDto());
        return "user/join";
    }

    @PostMapping("/join")
    public String join(@Validated(ValidationSequenceGroups.ValidationSeq.class) @ModelAttribute JoinFormDto joinFormDto, Errors errors) {

        if (errors.hasErrors()) {
            return "user/join";
        }

        User user = joinFormDto.mappingTo();

        userService.join(user);

        return "redirect:/tmp";
    }


    @GetMapping("/user/help/pwdInquiry")
    public String findPwdForm() {
        return "user/help/pwdInquiry";
    }

    @PostMapping("/user/help/pwdInquiry")
    public String findPwd(@ModelAttribute EmailWithNameAndId emailWithNameAndId) {

        String email = emailWithNameAndId.getEmail();
        String userName = emailWithNameAndId.getUserName();
        String userId = emailWithNameAndId.getUserId();

        User foundUser = userService.findUserByEmail(email);

        if (foundUser.getUserName().equals(userName)&&
                foundUser.getUserId().equals(userId)) {

            String tmpPwd = userService.changeUserPwdToTmp(foundUser);
            emailToUserService.sendEmailWithTmpPwd(email,tmpPwd);

            return "user/help/returnPwd";
        }

        return "/error";
    }
    @GetMapping("/user/help/idInquiry")
    public String findIdForm(@ModelAttribute EmailWithName emailWithName) {
        return "user/help/idInquiry";
    }

    @PostMapping("/user/help/idInquiry")
    public String findId(@ModelAttribute EmailWithName emailWithName, Model model) {

        String email = emailWithName.getEmail();
        String userName = emailWithName.getUserName();

        User foundUser = userService.findUserByEmail(email);

        if (foundUser.getUserName().equals(userName)) {
            model.addAttribute("userId",foundUser.getUserId());
            return "user/help/returnId";
        }

        return "/error";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "user/login";
    }

    @GetMapping("/profile/{userId}")
    public String userProfile(@PathVariable String userId, Principal principal, Model model) {

        User findUser = userService.findUserByUserId(userId);

        if (findUser == null) {
            throw new IllegalArgumentException(userId + "라는 유저는 존재하지 않습니다");
        }

        model.addAttribute("user", findUser);
        model.addAttribute("isItYou", false);

        if (userId.equals(principal.getName())) {
            model.addAttribute("isItYou", true);

            return "user/profile";
        }

        return "user/profile";
    }

    @GetMapping("/tmp")
    public String tmpTest() {
        return "user/tmp";
    }

}
