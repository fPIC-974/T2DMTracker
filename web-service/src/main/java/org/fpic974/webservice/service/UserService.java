package org.fpic974.webservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fpic974.webservice.dto.CredentialsDto;
import org.fpic974.webservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserDetailsService userDetailsService;
//    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    private final UserMapper userMapper;

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public UserDto signIn(CredentialsDto credentialsDto) {
        UserDetails user = userDetailsService.loadUserByUsername(credentialsDto.getUsername());

        if (passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())) {
            log.info("Passwords match ...");

            return UserDto.builder()
                    .username(user.getUsername())
                    .token(createToken(user))
                    .build();
        }

        throw new RuntimeException("Invalid password");
    }

    public UserDto validateToken(String token) {
        String login = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        UserDetails user = userDetailsService.loadUserByUsername(login);

//        return userMapper.toUserDto(user, createToken(user));

        return UserDto.builder()
                .username(user.getUsername())
                .token(createToken(user))
                .build();
    }

    private String createToken(UserDetails user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());

        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
