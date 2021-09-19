package com.project.board.user.validation;


import com.project.board.user.dto.JoinFormDto;
import com.project.board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinFormDtoValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return JoinFormDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // 아이디 중복 확인
        JoinFormDto joinFormDto = (JoinFormDto) target;
        if(userRepository.existByUserId(joinFormDto.getUserId())){
            errors.rejectValue("userId","invalid.userId",new Object[]{joinFormDto.getUserId()},"이미 사용중인 아이디 입니다.");
        };

        // 이메일 중복 확인
        if(userRepository.existByEmail(joinFormDto.getEmail())){
            errors.rejectValue("email","invalid.email",new Object[]{joinFormDto.getEmail()},"이미 사용중인 이메일 입니다.");
        };
    }
}