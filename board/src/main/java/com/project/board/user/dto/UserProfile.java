package com.project.board.user.dto;

import com.project.board.user.validation.ValidationSequenceGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UserProfile {

    private String userId;
    private String bio;
    private String userName;
    private LocalDate birthDate;
    private String email;

}
