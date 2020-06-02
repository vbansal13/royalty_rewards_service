package com.vbansal13.identity_service.service;

import com.vbansal13.identity_service.models.Merchant;

import com.vbansal13.identity_service.payload.request.MerchantRequest;
import com.vbansal13.identity_service.payload.response.MessageResponse;
import com.vbansal13.identity_service.repository.MerchantRepository;
import com.vbansal13.identity_service.repository.UserRepository;
import com.vbansal13.identity_service.repository.UserRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import java.util.Optional;


@Service
public class MerchantService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository roleRepository;

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    PasswordEncoder encoder;

    @Transactional
    public Optional<Merchant> registerMerchant(@Valid MerchantRequest registerRequest) {


        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.
                        getContext().getAuthentication().getPrincipal();

        Optional<Merchant> merchant = merchantRepository.findByUserAndBusinessName(userDetails.getEmail(),
                                                                        registerRequest.getBusinessName());

        if (merchant.isPresent()) {
            return null;
        }

        Merchant newMerchant = new Merchant(
                registerRequest.getBusinessName(),
                userDetails.getEmail(),
                registerRequest.getAddress(),
                registerRequest.getRewardType(),
                registerRequest.getRewardOrdersRequired(),
                registerRequest.getRewardDiscount()
        );

        merchantRepository.save(newMerchant);

        return Optional.of(newMerchant);
    }

    @Transactional
    public Pair<HttpStatus, Optional<MessageResponse>> updateMerchant(Long id, @Valid MerchantRequest registerRequest) {

        Optional<Merchant> merchant = merchantRepository.findById(id);

        if (!merchant.isPresent()) {
            return Pair.of(HttpStatus.BAD_REQUEST, Optional.of(new MessageResponse("Merchant not found.")));
        }

        Merchant mMerchant = merchant.get();

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();

        if (!mMerchant.getUserEmail().equalsIgnoreCase(userDetails.getEmail())) {
            return Pair.of(HttpStatus.UNAUTHORIZED, Optional.of(new MessageResponse("Merchant not found.")));
        }


        mMerchant.setName(registerRequest.getBusinessName());
        mMerchant.setAddress(registerRequest.getAddress());
        mMerchant.setRewardType(registerRequest.getRewardType());
        mMerchant.setRewardOrdersRequired(registerRequest.getRewardOrdersRequired());
        mMerchant.setRewardDiscount(registerRequest.getRewardDiscount());


        merchantRepository.save(mMerchant);
        return Pair.of(HttpStatus.OK, Optional.of(new MessageResponse("Success"));

    }

    @Transactional
    public Pair<HttpStatus, Optional<MessageResponse>> deleteMerchant(Long id) {
        Optional<Merchant> merchant = merchantRepository.findById(id);

        if (!merchant.isPresent()) {
            return Pair.of(HttpStatus.BAD_REQUEST, Optional.of(new MessageResponse("Merchant not found.")));
        }
        Merchant mMerchant = merchant.get();
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();

        if (!mMerchant.getUserEmail().equalsIgnoreCase(userDetails.getEmail())) {
            return Pair.of(HttpStatus.UNAUTHORIZED, Optional.of(new MessageResponse("Merchant not found.")));
        }

        merchantRepository.delete(mMerchant);
        return Pair.of(HttpStatus.NO_CONTENT, Optional.of(new MessageResponse("Success"));
    }

}
