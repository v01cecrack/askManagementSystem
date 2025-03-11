package org.example.taskmanagementsystem.controller;

import org.example.taskmanagementsystem.dto.AppUserGetDto;
import org.example.taskmanagementsystem.dto.AppUserPostDto;
import org.example.taskmanagementsystem.dto.AuthRequest;
import org.example.taskmanagementsystem.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AppUserService appUserService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        String token = appUserService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<AppUserGetDto> registration(@RequestBody AppUserPostDto user) {
        try {
            return new ResponseEntity<>(appUserService.register(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register-admin")
    public ResponseEntity<AppUserGetDto> registerAdmin(@RequestBody AppUserPostDto adminUser) {
        try {
            return new ResponseEntity<>(appUserService.registerAdmin(adminUser), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
