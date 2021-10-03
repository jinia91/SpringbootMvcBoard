package com.project.board.user.service;

import com.project.board.user.domain.User;
import com.project.board.user.dto.UserContext;
import com.project.board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public String join(User user) {
        pwdEncoding(user);
        userRepository.saveUser(user);
        log.info("info Join log = {}", user);
        return user.getUserId();
    }

    @Override
    public User findUserByUserId(String userId) {
        User findUser = userRepository.findUserById(userId);
        return findUser;
    }

    @Override
    public User findUserByEmail(String email) {
        User findUser = userRepository.findUserByEmail(email);
        return findUser;
    }

    @Override
    public boolean chkEmailValidatedWithName(String email, String userName) {
        User foundUser = findUserByEmail(email);
        if(foundUser == null) return false;
        if(foundUser.getUserName().equals(userName)) return true;
        return false;
    }

    @Override
    public boolean chkEmailValidatedWithNameAndId(String email, String userId, String userName) {
        User foundUser = findUserByEmail(email);
        if(foundUser == null) return false;
        if(foundUser.getUserName().equals(userName)&&
                foundUser.getUserId().equals(userId)) return true;
        return false;
    }

    @Override
    public String changeUserPwdToTmp(User user) {

        String tmpPwd = getRandomTmpPwd(8);
        user.setUserPwd(tmpPwd);
        pwdEncoding(user);
        userRepository.updatePwd(user);
        return tmpPwd;
    }

    @Override
    public void changeUserPwd(User user) {
        pwdEncoding(user);
        userRepository.updatePwd(user);
    }

    @Override
    public void changeUserProfile(User user) {
        userRepository.updateUserBioAndName(user);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User loginUser = userRepository.findUserById(userId);
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("Default"));

        UserContext userContext = new UserContext(loginUser, roles);

        return userContext;

    }

    private void pwdEncoding(User user) {
        user.setUserPwd(passwordEncoder.encode(user.getUserPwd()));
    }

    private String getRandomTmpPwd(int size){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&' };

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i=0; i<size; i++) {
            idx = sr.nextInt(len);
            sb.append(charSet[idx]);
        }
        return sb.toString();
    }
}
