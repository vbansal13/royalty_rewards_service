package com.vbansal13.identity_service.controller;

import com.vbansal13.identity_service.models.Membership;
import com.vbansal13.identity_service.models.User;
import com.vbansal13.identity_service.payload.request.MembershipRequest;
import com.vbansal13.identity_service.payload.response.MessageResponse;
import com.vbansal13.identity_service.service.MembershipService;
import com.vbansal13.identity_service.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/rewards")
@PreAuthorize("hasRole('USER')")
public class RewardsController {


    @Autowired
    private MembershipService membershipService;

    @GetMapping("/merchants")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getMerchants() {


        return ResponseEntity.ok(null);
    }

    @PostMapping("/membership")
    public ResponseEntity<?> addMembership(@RequestBody MembershipRequest membershipRequest) {


        Pair<HttpStatus, Optional<MessageResponse>> response = membershipService.addMembership(membershipRequest);
        return ResponseEntity.status(response.getFirst()).body(response.getSecond());
    }

    @DeleteMapping("/membership/{id}")
    public ResponseEntity<?> removeMembership(@PathVariable Long membershipId) {

        Pair<HttpStatus, Optional<MessageResponse>> response = membershipService.deleteMembership(membershipId);
        return ResponseEntity.status(response.getFirst()).body(response.getSecond());

    }

    @GetMapping("/membership")
    public ResponseEntity<?> getMembership() {
        return ResponseEntity.ok(membershipService.getAllMembership());
    }



}
