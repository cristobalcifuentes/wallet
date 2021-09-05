package com.alkemy.java.controller;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.alkemy.java.DTO.TokenDTO;
import com.alkemy.java.DTO.UserDto;
import com.alkemy.java.converter.UserConverter;
import com.alkemy.java.model.User;
import com.alkemy.java.model.Wallet;
import com.alkemy.java.service.IUserService;
import com.alkemy.java.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    private final String PREFIX = "Bearer ";

    @Autowired
    private IUserService userService;

    @Autowired
    private IWalletService walletService;


    private UserConverter userConverter = new UserConverter();

    @PostMapping("user")
    public ResponseEntity<TokenDTO> login(@RequestParam("user") String username, @RequestParam("password") String pwd) {


        User user = userService.login(username,pwd);
        String token = getJWTToken(user);
        user.setToken(token);
        userService.save(user);
        return new ResponseEntity<TokenDTO>(userConverter.convertEntityToDtoToken(user),HttpStatus.OK);

    }

    public String getJWTToken(User user) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId(String.valueOf(user.getUserId()))
                .setSubject(String.valueOf(user.getUserId()))
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return PREFIX + token;
    }

    @GetMapping("users")
    public ResponseEntity<List<UserDto>> listAllUsers() {
        return new ResponseEntity<List<UserDto>>(userConverter.convertEntityToDtoUserList(userService.getAllUsers()), HttpStatus.OK);
    }

}
