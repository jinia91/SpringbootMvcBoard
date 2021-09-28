package com.project.board.user.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter@Setter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class User {

        private int userUid;
        private String userId; // 아이디
        private String userPwd;
        private String userName;
        private String email;
        private LocalDate birthDate;
        private LocalDateTime createDate;
        private String bio;
}
