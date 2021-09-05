package com.alkemy.java.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.java.model.ResetToken;
import com.alkemy.java.model.User;
import com.alkemy.java.service.ILoginService;
import com.alkemy.java.service.IResetTokenService;
import com.alkemy.java.util.EmailUtil;
import com.alkemy.java.util.Mail;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IResetTokenService tokenService;
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	@Autowired
	private EmailUtil emailUtil;
	
	private final static String URL_RECOVER = "hhtp://localhost:4200/recover/";
	
	@PostMapping(value = "/sendMail", consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<Boolean> sendMail(@RequestBody String mail) {
		
		boolean response = false;
		try {
			User us = loginService.checkUserName(mail);
			if(us != null) {
				ResetToken token = new ResetToken();
				token.setToken(UUID.randomUUID().toString());
				token.setUser(us);
				token.setExpiration(10);
				tokenService.save(token);
				
				Mail email = new Mail();
				email.setFrom("neddForSpring@gmail.com");
				email.setTo(us.getUsername());
				email.setSubject("Restablecer contraseña E-Wallet");
				
				Map<String, Object> model = new HashMap<>();
				String url = URL_RECOVER + token.getToken();
				model.putIfAbsent("user", token.getUser().getUsername());
				model.put("resetUrl", url);
				email.setModel(model);
				emailUtil.sendMail(email);
				response = true;
				
			}
		}catch(Exception e) {
			return new ResponseEntity<Boolean>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Boolean>(response, HttpStatus.OK);	
	}
	
	@GetMapping(value = "/restore/check/{token}")
	public ResponseEntity<Boolean> restorePassword(@PathVariable("token") String token){
		boolean response = false;
		try {
			if(token != null && !token.isEmpty()) {
				ResetToken rt = tokenService.findByToken(token);
				if (rt != null) {
					if (!rt.isExpired()) {
						response = true;
					}
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Boolean>(response, HttpStatus.OK);
	}


	@PostMapping(value = "/restore/{token}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> restorePassword(@PathVariable("token") String token, @RequestBody String password) {
		boolean response = false;
		try {
			ResetToken rt = tokenService.findByToken(token);
			String hashPass = bcrypt.encode(password);
			response = loginService.changePassword(hashPass, rt.getUser().getUsername());
			tokenService.delete(rt);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Boolean>(response, HttpStatus.OK);
	}
}

