package com.project.board.user.controller;

import com.project.board.user.dto.EmailAddress;
import com.project.board.user.dto.EmailWithName;
import com.project.board.user.service.EmailToUserServiceImpl;
import com.project.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserRestController {

    private final EmailToUserServiceImpl mailToUserService;
    private final UserService userService;

    @PostMapping("/join/sendEmail")
    public @ResponseBody String sendEmail(@RequestBody EmailAddress emailAddress){

        String authNum = mailToUserService.sendEmailForAuth(emailAddress.getEmail());

        return authNum;
    }

    @PostMapping("/help/user/sendEmailWithName")
    public @ResponseBody
    ResponseEntity sendEmailWithName(@RequestBody EmailWithName emailWithName){

        String email = emailWithName.getEmail();
        String userName = emailWithName.getUserName();

        boolean isValidated = userService.chkEmailValidatedWithName(email, userName);

        if(isValidated) {
            String authNum = mailToUserService.sendEmailForAuth(email);
            return ResponseEntity.accepted().body(authNum);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
