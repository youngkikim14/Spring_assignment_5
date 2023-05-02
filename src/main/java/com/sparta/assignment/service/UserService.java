package com.sparta.assignment.service;

import com.sparta.assignment.dto.SigninRequestDto;
import com.sparta.assignment.dto.SignupRequestDto;
import com.sparta.assignment.entity.User;
import com.sparta.assignment.entity.UserRoleEnum;
import com.sparta.assignment.jwt.JwtUtil;
import com.sparta.assignment.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public void signup(SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()){
            throw new IllegalArgumentException("중복 된 아이디입니다"); //그런데 왜 굳이 IllegalArgumentException 써야 할까? 어차피 조건에서 중복 아이디 처리됐는데..
            }
        UserRoleEnum userRoleEnum = UserRoleEnum.USER;
        if (signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)){
            userRoleEnum = UserRoleEnum.ADMIN;
        } else {
            new IllegalArgumentException("관리자 암호가 틀려 관리자 등록이 불가합니다");
        }

        User user = new User(username, password, userRoleEnum);
        userRepository.save(user);
    }

    @Transactional
    public void signin(SigninRequestDto signinRequestDto, HttpServletResponse response) {
        String username = signinRequestDto.getUsername();
        String password = signinRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
                    );
                            // 이거 둘이 합치는 방법 없나... Repository가 optional 메서드라 user로 강제변환 밖에 안되네..
                            // if 문으로 둘이 묶어서 처리하고 싶은데...
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getUserRoleEnum()));
    }



}
