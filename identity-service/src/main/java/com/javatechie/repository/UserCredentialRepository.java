package com.javatechie.repository;

import com.javatechie.entity.UserCredential;
import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserCredentialRepository  extends JpaRepository<UserCredential,Integer> {

    @Query(name = "UserCredential.findByUniqueEmail", value = "select * from UserCredential u where BINARY u.email=:email", nativeQuery = true)
    UserCredential findByUniqueEmail(@Param("email") String email);

    Optional<UserCredential> findByEmail(String email);
}
