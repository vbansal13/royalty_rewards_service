package com.vbansal13.identity_service.repository;

import com.vbansal13.identity_service.models.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    Optional<Merchant> findById(Long id);

    @Query(value = "SELECT u FROM Merchant u where u.userEmail = :email and u.name = :business")
    Optional<Merchant> findByUserAndBusinessName(@Param("email") String email,
                                                 @Param("business") String businessName);
}
