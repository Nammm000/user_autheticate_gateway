package com.javatechie.controller;

import com.javatechie.dto.AuthRequest;
import com.javatechie.dto.SignupDTO;
import com.javatechie.entity.UserCredential;
import com.javatechie.repository.UserCredentialRepository;
import com.javatechie.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserCredentialRepository userCredentialRepository;

    @PostMapping("/register")
    public String addNewUser(@RequestBody SignupDTO signupDTO) {
        System.out.println("AuthController addNewUser");
        UserCredential user = userCredentialRepository.findByUniqueEmail(signupDTO.getEmail());
        if (user!=null) {
//            throw new RuntimeException("user already exists");
            return "user already exists";
        }
        return service.saveUser(signupDTO);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return service.generateToken(authRequest.getEmail());
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}
