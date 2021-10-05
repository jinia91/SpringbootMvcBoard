package com.project.board.user.dto;

import com.project.board.user.validation.ValidationSequenceGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserProfilePwd {

    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요", groups = ValidationSequenceGroups.First.class)
    @Pattern(regexp = "(?=.*\\d{1,30})(?=.*[~`!@#$%\\^&*()-+=]{1,30})(?=.*[a-zA-Z]{1,30}).{8,30}$",
            message = "비밀번호는 영문/숫자/특수문자를 1회 이상 사용하여 비밀번호를 작성해주세요", groups = ValidationSequenceGroups.Second.class)
    @Size(min = 8, max = 30, message = "비밀번호의 길이는 8자 이상 30자 이하로 작성해주세요", groups = ValidationSequenceGroups.Third.class)
    private String beforePwd;

    @NotBlank(message = "비밀번호를 입력해주세요", groups = ValidationSequenceGroups.First.class)
    @Pattern(regexp = "(?=.*\\d{1,30})(?=.*[~`!@#$%\\^&*()-+=]{1,30})(?=.*[a-zA-Z]{1,30}).{8,30}$",
            message = "비밀번호는 영문/숫자/특수문자를 1회 이상 사용하여 비밀번호를 작성해주세요", groups = ValidationSequenceGroups.Second.class)
    @Size(min = 8, max = 30, message = "비밀번호의 길이는 8자 이상 30자 이하로 작성해주세요", groups = ValidationSequenceGroups.Third.class)
    private String userPwd;
}
