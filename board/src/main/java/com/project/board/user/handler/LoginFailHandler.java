package com.project.board.user.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errMsg = "error";

        if(exception instanceof AuthenticationServiceException){
            errMsg = "InvalidId";
            request.setAttribute("errMsg", errMsg);

        }
        else if(exception instanceof BadCredentialsException){
            errMsg = "InvalidIdOrPwd";
            request.setAttribute("errMsg", errMsg);
            log.info("비밀번호 틀림");
        }

        setDefaultFailureUrl("/login?error="+errMsg);
        super.onAuthenticationFailure(request, response, exception);
    }
}
