package com.vbansal13.identity_service.repository;

import com.vbansal13.identity_service.models.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Collection;

import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    Optional<Membership> findById(Long id);


    @Query(value = "SELECT u FROM Membership u where u.userEmail = :email and u.merchantId = :merchantId")
    Optional<Membership> findByUserAndMerchantId(@Param("email") String email,
                                                 @Param("merchantId") Long merchantId);

    @Query(value = "SELECT u FROM Membership u where u.userEmail = :email")
    Collection<Membership> findByUser(@Param("email") String email);

}
