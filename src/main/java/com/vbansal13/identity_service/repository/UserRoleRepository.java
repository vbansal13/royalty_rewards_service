package com.vbansal13.identity_service.repository;

import com.vbansal13.identity_service.models.RoleType;
import com.vbansal13.identity_service.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByRole(RoleType role);

}

