package org.fpic974.webservice.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fpic974.webservice.dto.CredentialsDto;
import org.fpic974.webservice.dto.UserDto;
import org.fpic974.webservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/signIn")
    public String signIn(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        log.info("Trying to login {}", username);
        CredentialsDto credentialsDto = CredentialsDto.builder()
                .username(username)
                .password(password)
                .build();

        UserDto user = userService.signIn(credentialsDto);

        if (user == null) {
            return "redirect:/login?error";
        }

        Cookie cookie = new Cookie("Authorization", user.getToken());

        response.addCookie(cookie);

        return "redirect:/web/patient";
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
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        ModelAndView mav = new ModelAndView();

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                mav.addObject("error", "Page Not Found");
                return "error";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                mav.addObject("error", "Internal Server Error");
                return "error";
            }
        }
        return "error";
    }
}
