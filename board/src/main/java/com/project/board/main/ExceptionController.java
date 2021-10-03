package com.project.board.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler
    public String handleRuntimeException(Principal principal, HttpServletRequest req, RuntimeException e) {
        if (principal != null) {
            log.info("'{}' requested '{}'", principal.getName(), req.getRequestURI());
        } else {
            log.info("requested '{}'", req.getRequestURI());
        }
        log.error("bad request", e);
        return "/error";
    }
}
