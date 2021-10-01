package com.project.board.config;

import com.project.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    // 인가 필요없는 정적 리소스 보안필터 무시처리
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("js/**","/js/user/**","/node_modules/**","/resources/**","/favicon.ico")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 인가 API
        http
                .authorizeRequests()
                    .mvcMatchers("/", "/join", "/join/sendEmail", "/tmp","/user/help/**")
                    .permitAll()
                    .anyRequest().authenticated();

        // 인증 API
        http
                //로그인
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("userId")
                    .passwordParameter("userPwd")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login")
                    .permitAll()

                //로그아웃
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID","remember-me")

                //csrf
                .and().csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

        ;

    }


}
