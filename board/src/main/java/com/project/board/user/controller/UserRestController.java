package com.project.board.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserRestController {

    @PostMapping("/join/sendEmail")
    public String emailSend(){


        return "ok";

    }

}
