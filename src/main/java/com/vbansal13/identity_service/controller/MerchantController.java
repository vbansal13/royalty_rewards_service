package com.vbansal13.identity_service.controller;

import com.vbansal13.identity_service.payload.request.MerchantRequest;
import com.vbansal13.identity_service.payload.response.MessageResponse;
import com.vbansal13.identity_service.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/businesses")
@PreAuthorize("hasRole('USER')")
public class MerchantController {


    @Autowired
    private MerchantService merchantService;



    @PostMapping("/merchant")
    public ResponseEntity<?> addMerchant(@RequestBody @Valid MerchantRequest merchantRequest) {


        if (!merchantService.registerMerchant(merchantRequest).isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Merchant already registered."));
        }
        return ResponseEntity.ok(new MessageResponse("Merchant registered successfully!"));
    }

    @PutMapping("/merchant/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable Long id,
                                            @RequestBody @Valid MerchantRequest merchantRequest) {

        Pair<HttpStatus, Optional<MessageResponse>> response = merchantService.updateMerchant(id, merchantRequest);

        return ResponseEntity.status(response.getFirst()).body(response.getSecond());
    }

    @DeleteMapping("/merchant/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable Long id) {
        Pair<HttpStatus, Optional<MessageResponse>> response = merchantService.deleteMerchant(id);
        return ResponseEntity.status(response.getFirst()).body(response.getSecond());
    }

}
