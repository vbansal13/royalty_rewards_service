package com.vbansal13.identity_service.service;

import com.vbansal13.identity_service.models.Membership;
import com.vbansal13.identity_service.models.Merchant;
import com.vbansal13.identity_service.models.User;
import com.vbansal13.identity_service.payload.request.MembershipRequest;
import com.vbansal13.identity_service.payload.response.MembershipResponse;
import com.vbansal13.identity_service.payload.response.MessageResponse;
import com.vbansal13.identity_service.repository.MembershipRepository;
import com.vbansal13.identity_service.repository.MerchantRepository;
import com.vbansal13.identity_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
public class MembershipService {


    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public Pair<HttpStatus, Optional<MessageResponse>> addMembership(MembershipRequest membershipRequest) {


        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                                            getAuthentication().getPrincipal();

        if (membershipRepository.findByUserAndMerchantId(userDetails.getEmail(),
                                membershipRequest.getMerchantId()).isPresent()) {
            return Pair.of(HttpStatus.BAD_REQUEST,
                    Optional.of(new MessageResponse("User is already a member of this merchant.")));
        }

        Optional<Merchant> merchant = merchantRepository.findById(membershipRequest.getMerchantId());

        if (!merchant.isPresent()) {
            return Pair.of(HttpStatus.BAD_REQUEST,
                    Optional.of(new MessageResponse("Merchant not found.")));
        }

        Membership mShip = new Membership(userDetails.getUsername(), userDetails.getId(),
                                membershipRequest.getMerchantId());
        membershipRepository.save(mShip);

        return Pair.of(HttpStatus.OK, Optional.of(new MessageResponse("Success"));


    }

    @Transactional
    public Pair<HttpStatus, Optional<MessageResponse>> deleteMembership(Long membershipId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                                            getAuthentication().getPrincipal();

        Optional<Membership> membership = membershipRepository.findById(membershipId);
        if (!membership.isPresent()) {
            return Pair.of(HttpStatus.UNAUTHORIZED, Optional.of(new MessageResponse("Membership not found.")));

        }

        if (!membership.get().getUserEmail().equalsIgnoreCase(userDetails.getEmail())) {
            return Pair.of(HttpStatus.UNAUTHORIZED, Optional.of(new MessageResponse("Membership not found.")));
        }
        membershipRepository.delete(membership.get());

        return Pair.of(HttpStatus.NO_CONTENT, Optional.of(new MessageResponse("Success"));
    }

    public Collection<MembershipResponse> getAllMembership() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                                                getAuthentication().getPrincipal();

        Collection<Membership> memberships = membershipRepository.findByUser(userDetails.getEmail());

        Collection<MembershipResponse> membershipResponses = new ArrayList<MembershipResponse>();

        for (Membership membership : memberships) {

            Optional<Merchant> merchant = merchantRepository.findById(membership.getMerchantId());
            if (!merchant.isPresent()) continue;

            MembershipResponse membershipResponse = new MembershipResponse(membership.getId(),
                                                                            merchant.get());
            membershipResponses.add(membershipResponse);
        }
        return membershipResponses;
    }
}
