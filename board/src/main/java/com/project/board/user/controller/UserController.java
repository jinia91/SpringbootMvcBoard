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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JoinFormDtoValidator joinFormDtoValidator;
    private final EmailToUserServiceImpl emailToUserService;
    private final ModelMapper modelMapper;

    @InitBinder("joinFormDto")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(joinFormDtoValidator);
    }

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute(new JoinFormDto());
        return "user/form/join";
    }

    @PostMapping("/join")
    public String join(@Validated(ValidationSequenceGroups.ValidationSeq.class) @ModelAttribute JoinFormDto joinFormDto, Errors errors) {

        if (errors.hasErrors()) {
            return "user/form/join";
        }

        User user = modelMapper.map(joinFormDto,User.class);
        userService.join(user);

        return "redirect:/joinSuc";
    }

    @GetMapping("/joinSuc")
    public String joinSuc() {
        return "/user/joinSuc";
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

        boolean equals = foundUser.getUserId().equals(userId);

        if (foundUser.getUserName().equals(userName)&&
                foundUser.getUserId().equals(userId)) {

            String tmpPwd = userService.changeUserPwdToTmp(foundUser);
            emailToUserService.sendEmailWithTmpPwd(email,tmpPwd);

            return "user/help/returnPwd";
        }

        throw new IllegalArgumentException("입력한 계정정보와 접근하려는 계정정보가 불일치");
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

        throw new IllegalArgumentException("입력한 계정정보와 접근하려는 계정정보가 불일치");
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error",required = false) String error, Model model) {

        if(error != null && error.equals("InvalidIdOrPwd")) {
            model.addAttribute("errMsg", "아이디나 비밀번호가 틀렸습니다");
        }else if(error != null && error.equals("InvalidId")){
            model.addAttribute("errMsg", "아이디가 존재하지 않습니다");
        }
        return "user/form/login";
    }

}
