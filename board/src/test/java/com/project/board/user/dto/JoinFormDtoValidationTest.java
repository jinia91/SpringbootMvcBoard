package com.project.board.user.dto;

import com.project.board.user.validation.ValidationSequenceGroups;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.Set;


class JoinFormDtoValidationTest {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    @DisplayName("JoinForm 검증 성공 테스트")
    void validationTest() throws Exception {
    // given
    JoinFormDto joinFormDto = new JoinFormDto();
    joinFormDto.setUserId("jinia91");
    joinFormDto.setUserPwd("qwer1234!");
    joinFormDto.setUserName("홍길동");
    joinFormDto.setBirthDate(LocalDate.of(2000,10,3));
    joinFormDto.setEmail("qwer@gmail.com");
    // when
        Set<ConstraintViolation<JoinFormDto>> violations = validator.validate(joinFormDto,ValidationSequenceGroups.ValidationSeq.class);
    // then
        for (ConstraintViolation<JoinFormDto> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation = " + violation.getMessage());
        }
        Assertions.assertThat(violations.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("JoinForm 검증 실패 테스트")
    void validationFailTestV1() throws Exception {
        // given
        JoinFormDto joinFormDto = new JoinFormDto();
        joinFormDto.setUserId("j!ini@a91"); // 특수문자 포함 아이디
        joinFormDto.setUserPwd("qwer1234"); // 특수문자 미포함 비밀번호
        joinFormDto.setUserName("홍길동");
        joinFormDto.setBirthDate(LocalDate.of(2030,10,3)); // 비정상 생년월일
        joinFormDto.setEmail("qwersdfsdf"); // 비정상 이메일
        // when
        Set<ConstraintViolation<JoinFormDto>> violations = validator.validate(joinFormDto,ValidationSequenceGroups.ValidationSeq.class);
        // then
        for (ConstraintViolation<JoinFormDto> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation = " + violation.getMessage());
        }
        Assertions.assertThat(violations.size()).isEqualTo(4);

    }

    @Test
    @DisplayName("JoinForm 검증 실패 테스트v2")
    void validationFailTestV2() throws Exception {
        // given
        JoinFormDto joinFormDto = new JoinFormDto();
        joinFormDto.setUserId("ㅎㅇㅀㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊ"); // 비정상 아이디
        joinFormDto.setUserPwd("qwer@1234");
        joinFormDto.setUserName("마ㅇㄴㄹㄹ"); // 비정상 이름
        joinFormDto.setBirthDate(LocalDate.of(2000,10,3));
        joinFormDto.setEmail("qwers@dfsdf");
        // when
        Set<ConstraintViolation<JoinFormDto>> violations = validator.validate(joinFormDto,ValidationSequenceGroups.ValidationSeq.class);
        // then
        for (ConstraintViolation<JoinFormDto> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation = " + violation.getMessage());
        }
        Assertions.assertThat(violations.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("JoinForm 검증 실패 테스트v3 Length")
    void validationFailTestV3() throws Exception {
        // given
        JoinFormDto joinFormDto = new JoinFormDto();
        joinFormDto.setUserId("SDFsdfsdfsdfsdfs"); // 비정상 아이디 길이
        joinFormDto.setUserPwd("qwer@1234");
        joinFormDto.setUserName("마미미미미미미미미미미미미미미미미미"); // 비정상 이름길이
        joinFormDto.setBirthDate(LocalDate.of(2000,10,3));
        joinFormDto.setEmail("qwers@dfsdf");
        // when
        Set<ConstraintViolation<JoinFormDto>> violations = validator.validate(joinFormDto,ValidationSequenceGroups.ValidationSeq.class);
        // then
        for (ConstraintViolation<JoinFormDto> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation = " + violation.getMessage());
        }
        Assertions.assertThat(violations.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("JoinForm 검증 실패 테스트v4 NullTest")
    void validationFailTestV4() throws Exception {
        // given
        JoinFormDto joinFormDto = new JoinFormDto();
        joinFormDto.setUserId("");
        joinFormDto.setUserPwd("");
        joinFormDto.setUserName("");
        joinFormDto.setBirthDate(LocalDate.of(2000,10,3));
        joinFormDto.setEmail("");
        // when
        Set<ConstraintViolation<JoinFormDto>> violations = validator.validate(joinFormDto,ValidationSequenceGroups.ValidationSeq.class);
        // then
        for (ConstraintViolation<JoinFormDto> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation = " + violation.getMessage());
        }
        Assertions.assertThat(violations.size()).isEqualTo(4);
    }
}