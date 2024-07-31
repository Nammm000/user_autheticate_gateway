package com.javatechie.controller;

import com.javatechie.dto.OrderResponseDTO;
import com.javatechie.service.SwiggyAppService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swiggy")
public class SwiggyAppController {

    @Autowired
    private SwiggyAppService service;

    @GetMapping("/home")
    public String greetingMessage() {
        return service.greeting();
    }

    @GetMapping("/{orderId}")
    public OrderResponseDTO checkOrderStatus(@PathVariable String orderId) {
        return service.checkOrderStatus(orderId);
    }

    // check if 'Accept' header in request contains passed content type
    private boolean acceptHeaderContainsContentType(HttpServletRequest request, String contentType) {
        String acceptHeader = request.getHeader("Accept");

        if (acceptHeader == null) {
            return false;
        }

        return acceptHeader.contains(contentType);
    }

    private boolean isRequestFromBrowser(HttpServletRequest request) {
        return acceptHeaderContainsContentType(request, MediaType.TEXT_HTML.toString());
    }

//    private String wrapLinkInHtmlTag(String link) {
//        return String.format("<a href='%s'>%s</a>", link, link);
//    }
}
