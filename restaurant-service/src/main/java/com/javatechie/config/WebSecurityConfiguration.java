package com.javatechie.config;

import com.javatechie.filter.CustomGatewayFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

//    @Bean
//    @Order(1)
//    public SecurityFilterChain ipFilterChain(HttpSecurity http) throws Exception {
//        return http.authorizeRequests()
//                .anyRequest().hasIpAddress("localhost:8080")
//                .and().csrf(csrf -> csrf.disable())
//                .build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests( (auth) -> auth
                        .anyRequest().access(hasIPAddress("localhost")) // .access(hasIPAddress("localhost:8080")).permitAll() 127.0.0.1
                )
                .sessionManagement( (sess) -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new CustomGatewayFilter(), ConcurrentSessionFilter.class)
                .build();
    }

    private static AuthorizationManager<RequestAuthorizationContext> hasIPAddress(String ipAddress) {
//        System.out.println("hasIPAddress");
        IpAddressMatcher ipAddressMatcher = new IpAddressMatcher(ipAddress);
        return (authentication, context) -> {
            HttpServletRequest request = context.getRequest();
            return new AuthorizationDecision(ipAddressMatcher.matches(request));
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
