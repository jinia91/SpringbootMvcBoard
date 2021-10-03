package com.project.board.user.validation;


import com.project.board.user.domain.User;
import com.project.board.user.dto.JoinFormDto;
import com.project.board.user.dto.UserProfilePwd;
import com.project.board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class EditPwdValidator implements Validator {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserProfilePwd.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // 이전 비밀번호 일치 검증
        UserProfilePwd userProfilePwd = (UserProfilePwd) target;
        User user = userRepository.findUserById(userProfilePwd.getUserId());

        if(!passwordEncoder.matches(userProfilePwd.getBeforePwd(),user.getUserPwd())){
            errors.rejectValue("beforePwd","invalid.beforePwd",new Object[]{userProfilePwd.getBeforePwd()},"비밀번호가 틀렸습니다");
        };

    }
}