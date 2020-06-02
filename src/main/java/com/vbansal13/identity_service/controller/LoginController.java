package com.vbansal13.identity_service.controller;


import com.vbansal13.identity_service.jwt.JwtUtils;
import com.vbansal13.identity_service.models.*;
import com.vbansal13.identity_service.payload.request.LoginRequest;
import com.vbansal13.identity_service.payload.request.MerchantRequest;
import com.vbansal13.identity_service.payload.request.SignupRequest;
import com.vbansal13.identity_service.payload.response.JwtResponse;
import com.vbansal13.identity_service.payload.response.MessageResponse;
import com.vbansal13.identity_service.repository.UserRepository;
import com.vbansal13.identity_service.repository.UserRoleRepository;
import com.vbansal13.identity_service.service.MerchantService;
import com.vbansal13.identity_service.service.UserDetailsImpl;

import com.vbansal13.identity_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;



    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository roleRepository;

    @Autowired
    MerchantService merchantService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;



    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail()));
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserProfile() {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username: " + userDetails.getUsername()));

        return ResponseEntity.ok(user);

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        userService.registerUser(signUpRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    /*
    @PostMapping("/signup-merchant")
    public ResponseEntity<?> registerUser(@RequestBody @Valid MerchantRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        merchantService.registerMerchant(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("Merchant registered successfully!"));
    }*/
}
