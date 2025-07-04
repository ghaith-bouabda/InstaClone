package com.example.InstaClone;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class demoController {
    @PostMapping("/hello")
    @PreAuthorize("hasRole(client_user)")
    public String hello() {
        return "hello";
    }
    @PostMapping("/hello/admin")
    @PreAuthorize("hasRole(client_admin)")
    public String hello2() {
        return "hello";
    }
}
