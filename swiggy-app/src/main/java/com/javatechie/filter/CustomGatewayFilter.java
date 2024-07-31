package com.javatechie.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class CustomGatewayFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String proxyForwardedHostHeader = request.getHeader("X-Forwarded-Host");
        System.out.println(proxyForwardedHostHeader);

        if (proxyForwardedHostHeader == null || !proxyForwardedHostHeader.equals("localhost:8080")) {
            System.out.println("Unauthorized Access, you should pass through the API gateway");

            ((HttpServletResponse) response).addHeader("SC_UNAUTHORIZED", "Provided Information is Invalid");
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Provided Information is Invalid");
        }
        chain.doFilter(request, response);
    }
}
