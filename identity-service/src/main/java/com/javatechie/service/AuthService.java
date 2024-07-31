package com.javatechie.service;


import com.javatechie.dto.SignupDTO;
import com.javatechie.entity.UserCredential;
import com.javatechie.repository.UserCredentialRepository;

import com.verifalia.api.VerifaliaRestClient;
import com.verifalia.api.emailvalidations.WaitingStrategy;
import com.verifalia.api.emailvalidations.models.Validation;
import com.verifalia.api.emailvalidations.models.ValidationEntry;
import com.verifalia.api.exceptions.VerifaliaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    VerifaliaRestClient verifalia = new VerifaliaRestClient("cea8ac456a4248b28290e9ce1c984b53", "VerifaliaNam123@.");

    public String saveUser(SignupDTO signupDTO) {
        System.out.println("AuthService saveUser");
        String email = signupDTO.getEmail();

        try {
            Validation validation = verifalia.getEmailValidations()
                    .submit(email, new WaitingStrategy(true));
            ValidationEntry entry = validation.getEntries().get(0);
            System.out.println("Validation: " + validation);
            System.out.println("Entry: " + entry);

            if (entry.getClassification().toString()=="Deliverable") {
                if (entry.getStatus().toString()=="Success") {
                    System.out.println(validation.getOverview().getId());

//                verifalia.getEmailValidations()
//                        .delete(validation.getOverview().getId());
                    UserCredential user = new UserCredential();
                    user.setName(signupDTO.getName());
                    user.setEmail(email);
                    user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
//                UserCredential createdUser = repository.save(user);
                    repository.save(user);
                    return "User added to the system";
                }

                return "Invalid email address";
            }
            return "Can't Deliverate";
        } catch (VerifaliaException ve) {
            ve.printStackTrace();
            System.out.println(ve.getMessage());
            return "VerifaliaException";
        }
    }

    public String generateToken(String email) {
        return jwtService.generateToken(email);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }


}
