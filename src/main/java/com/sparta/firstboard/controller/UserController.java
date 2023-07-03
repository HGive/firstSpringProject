package com.sparta.firstboard.controller;

import com.sparta.firstboard.dto.LoginRequestDto;
import com.sparta.firstboard.dto.SignupRequestDto;
import com.sparta.firstboard.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/signup")
    public String signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult ,HttpServletResponse res) {

        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return "회원가입 실패 , code: " + res.getStatus() ;
        }
        userService.signup(requestDto);
        return "회원가입 성공 , code: " + res.getStatus();
    }

    @PostMapping("/user/login")   //필터에서 해도 됨.
    public String login(@RequestBody LoginRequestDto requestDto , HttpServletResponse res){
        try {
            userService.login(requestDto ,res);
        } catch (Exception e) {
            return "로그인 실패 , code : " + res.getStatus();
        }

        return "로그인 성공 , code : " + res.getStatus();
    }
}
