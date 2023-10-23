package org.fpic974.webservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public UserDto signIn(CredentialsDto credentialsDto) {
        UserDetails user = userDetailsService.loadUserByUsername(credentialsDto.getUsername());
        UserDto userDto = null;

        if (passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())) {
            log.info("Passwords match ...");

            userDto = UserDto.builder()
                    .username(user.getUsername())
                    .token(createToken(user))
                    .build();
        }

//        throw new RuntimeException("Invalid password");

        return userDto;
    }


    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public UserDto validateToken(String token) {

        String login = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        UserDetails user = userDetailsService.loadUserByUsername(login);

        return UserDto.builder()
                .username(user.getUsername())
                .token(createToken(user))
                .build();
    }

    public String createToken(UserDetails user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());

        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
