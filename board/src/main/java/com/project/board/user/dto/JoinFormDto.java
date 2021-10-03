package com.project.board.user.dto;

import com.project.board.user.domain.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

import static com.project.board.user.validation.ValidationSequenceGroups.*;

@Data
public class JoinFormDto {

    @NotBlank(message = "아이디를 작성해주세요",groups = First.class)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영문/숫자 조합으로만 작성해주세요", groups = Second.class)
    @Size(min = 4,max = 15, message = "아이디의 길이는 4자 이상 15자 이하로 작성해주세요",groups = Third.class)
    private String userId; // 아이디

    @NotBlank(message = "비밀번호를 입력해주세요", groups = First.class)
    @Pattern(regexp = "(?=.*\\d{1,30})(?=.*[~`!@#$%\\^&*()-+=]{1,30})(?=.*[a-zA-Z]{1,30}).{8,30}$",
             message = "비밀번호는 영문/숫자/특수문자를 1회 이상 사용하여 비밀번호를 작성해주세요", groups = Second.class)
    @Size(min = 8, max = 30, message = "비밀번호의 길이는 8자 이상 30자 이하로 작성해주세요", groups = Third.class)
    private String userPwd;

    @NotBlank(message = "이름을 입력해주세요", groups = First.class)
    @Pattern(regexp = "^[가-힣|a-z|A-Z]+$", message = "정상적인 이름을 작성해주세요", groups = Second.class)
    @Size(min = 1, max = 10, message = "이름은 10자 이내로 작성해주세요", groups = Third.class)
    private String userName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "생년월일을 기입해주세요", groups = First.class)
    @Past(message = "생년월일이 적절하지 않습니다", groups = Second.class)
    private LocalDate birthDate;

    @NotBlank(message = "이메일을 입력해주세요", groups = First.class)
    @Email(message = "이메일이 적절하지 않습니다", groups = Second.class)
    @Size(max = 50, message = "이메일이 적절하지 않습니다", groups = Third.class)
    private String email;

}
