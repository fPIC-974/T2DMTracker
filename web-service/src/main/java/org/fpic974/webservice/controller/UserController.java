package org.fpic974.webservice.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fpic974.webservice.dto.CredentialsDto;
import org.fpic974.webservice.dto.UserDto;
import org.fpic974.webservice.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    /*@PostMapping("/signIn")
//    public ResponseEntity<UserDto> signIn(@RequestBody CredentialsDto credentialsDto) {
    public ResponseEntity<UserDto> signIn(@RequestParam String username, @RequestParam String password) {
        log.info("Trying to login {}", username);
        CredentialsDto credentialsDto = CredentialsDto.builder()
                .username(username)
                .password(password)
                .build();
        return ResponseEntity.ok(userService.signIn(credentialsDto));
    }*/

    @PostMapping("/signIn")
//    public ResponseEntity<UserDto> signIn(@RequestBody CredentialsDto credentialsDto) {
    public ResponseEntity<UserDto> signIn(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        log.info("Trying to login {}", username);
        CredentialsDto credentialsDto = CredentialsDto.builder()
                .username(username)
                .password(password)
                .build();

        UserDto user = userService.signIn(credentialsDto);

        Cookie cookie = new Cookie("Authorization", user.getToken());

        response.addCookie(cookie);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/validateToken")
    public ResponseEntity<UserDto> validate(@RequestParam String token) {
        log.info("Trying to validate token {}", token);
        return ResponseEntity.ok(userService.validateToken(token));
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }
}
