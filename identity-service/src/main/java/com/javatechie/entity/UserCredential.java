package com.javatechie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//@NamedQuery(name = "UserCredential.findByUniqueEmail", query = "select u from UserCredential u where BINARY u.email=:email")

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredential implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int id;
    private String name;

    @Column(unique=true)
    private String email;
    private String password;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
