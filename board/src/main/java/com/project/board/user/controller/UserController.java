package com.project.board.user.controller;

import com.project.board.user.domain.User;
import com.project.board.user.dto.JoinFormDto;
import com.project.board.user.service.UserService;
import com.project.board.user.validation.JoinFormDtoValidator;
import com.project.board.user.validation.ValidationSequenceGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JoinFormDtoValidator joinFormDtoValidator;
    private final PasswordEncoder passwordEncoder;

    @InitBinder("joinFormDto")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(joinFormDtoValidator);
    }

    @GetMapping("/join")
    public String joinForm(Model model){
        model.addAttribute(new JoinFormDto());
        return "user/join";
    }

    @PostMapping("/join")
    public String join(@Validated(ValidationSequenceGroups.ValidationSeq.class) @ModelAttribute JoinFormDto joinFormDto, Errors errors){

        if(errors.hasErrors()){
            return "user/join";
        }

        pwdEncoding(joinFormDto);
        User user = joinFormDto.mappingTo();
        userService.join(user);
        return "redirect:/tmp";
    }


    private void pwdEncoding(JoinFormDto joinFormDto) {
        joinFormDto.setUserPwd(passwordEncoder.encode(joinFormDto.getUserPwd()));
    }


    @GetMapping("/login")
    public String loginForm(Model model){
        return "user/login";
    }

    @GetMapping("/tmp")
    public String tmpTest(){
        return "user/tmp";
    }


}
