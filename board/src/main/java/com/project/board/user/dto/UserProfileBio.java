package com.project.board.user.dto;

import com.project.board.user.validation.ValidationSequenceGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UserProfileBio {

    private String userId;
    @Size(max=40)
    private String bio;
    @NotBlank(message = "이름을 입력해주세요", groups = ValidationSequenceGroups.First.class)
    @Pattern(regexp = "^[가-힣|a-z|A-Z]+$", message = "정상적인 이름을 작성해주세요", groups = ValidationSequenceGroups.Second.class)
    @Size(min = 1, max = 10, message = "이름은 10자 이내로 작성해주세요", groups = ValidationSequenceGroups.Third.class)
    private String userName;

}
