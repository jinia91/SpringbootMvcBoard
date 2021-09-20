package com.project.board.user.controller;

import com.project.board.user.dto.EmailAddress;
import com.project.board.user.service.EmailToUserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserRestController {

    private final EmailToUserServiceImpl mailToUserService;

    @PostMapping("/join/sendEmail")
    public @ResponseBody String sendEmail(@RequestBody EmailAddress emailAddress){

        String authNum = mailToUserService.sendEmailForJoinAuth(emailAddress.getEmail());

        return authNum;
    }

}
