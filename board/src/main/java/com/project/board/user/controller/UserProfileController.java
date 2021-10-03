package com.project.board.user.controller;

import com.project.board.user.domain.User;
import com.project.board.user.dto.UserProfile;
import com.project.board.user.dto.UserProfileBio;
import com.project.board.user.dto.UserProfilePwd;
import com.project.board.user.service.UserService;
import com.project.board.user.validation.EditPwdValidator;
import com.project.board.user.validation.ValidationSequenceGroups;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final EditPwdValidator editPwdValidator;

    @InitBinder("userProfilePwd")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(editPwdValidator);
    }


    @GetMapping("/profile/{userId}")
    public String userProfile(@PathVariable String userId, Principal principal, Model model) {

        User findUser = userService.findUserByUserId(userId);

        if (findUser == null) {
            throw new IllegalArgumentException(userId + "라는 유저는 존재하지 않습니다");
        }

        UserProfile userProfile = modelMapper.map(findUser,UserProfile.class);

        model.addAttribute("user", userProfile);
        model.addAttribute("isItYou", false);

        if (userId.equals(principal.getName())) {
            model.addAttribute("isItYou", true);

            return "user/profile";
        }

        return "user/profile";
    }

    @GetMapping("/profile/{userId}/edit")
    public String userProfileEditForm(@PathVariable String userId, Principal principal, Model model){

        if(!userId.equals(principal.getName())){
            throw new IllegalArgumentException("현재 로그인 계정과 접근하려는 계정정보가 불일치");
        }

        User userByUserId = userService.findUserByUserId(userId);
        UserProfileBio userProfile = modelMapper.map(userByUserId, UserProfileBio.class);
        model.addAttribute("user", userProfile);
        return "/user/form/userProfileEditForm";
    }

    @PostMapping("/profile/{userId}/edit")
    public String userProfileEdit(@PathVariable String userId, Principal principal,
                                  @Validated(ValidationSequenceGroups.ValidationSeq.class) @ModelAttribute UserProfileBio userProfileBio, Errors errors){
        if(!userId.equals(principal.getName())){
            throw new IllegalArgumentException("현재 로그인 계정과 접근하려는 계정정보가 불일치");
        }
        if (errors.hasErrors()) {
            return "/user/form/userProfileEditForm";
        }

        User userByUserId = userService.findUserByUserId(userId);
        modelMapper.map(userProfileBio,userByUserId);
        userService.changeUserProfile(userByUserId);

        return "redirect:/profile/{userId}";
    }

    @GetMapping("/profile/{userId}/edit/pwd")
    public String userPwdEditForm(@PathVariable String userId, Principal principal,UserProfilePwd userProfilePwd, Model model){

        if(!userId.equals(principal.getName())){
            throw new IllegalArgumentException("현재 로그인 계정과 접근하려는 계정정보가 불일치");
        }
        model.addAttribute("userProfilePwd", userProfilePwd);
        return "user/form/userPwdEditForm";
    }

    @PostMapping("/profile/{userId}/edit/pwd")
    public String userPwdEdit(@PathVariable String userId, Principal principal,
                              @Validated(ValidationSequenceGroups.ValidationSeq.class) @ModelAttribute UserProfilePwd userProfilePwd, Errors errors){

        if(!userId.equals(principal.getName())){
            throw new IllegalArgumentException("현재 로그인 계정과 접근하려는 계정정보가 불일치");
        }

        if (errors.hasErrors()) {
            return "user/form/userPwdEditForm";
        }

        User user = userService.findUserByUserId(userId);
        modelMapper.map(userProfilePwd,user);
        userService.changeUserPwd(user);

        return "redirect:/profile/{userId}/edit/pwdSuc";
    }

    @GetMapping("/profile/{userId}/edit/pwdSuc")
    public String userPwdEditSuc(@PathVariable String userId){

        return "/user/editPwdSuc";
    }


}
