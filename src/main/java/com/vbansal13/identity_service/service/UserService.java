package com.vbansal13.identity_service.service;

import com.vbansal13.identity_service.models.RoleType;
import com.vbansal13.identity_service.models.User;
import com.vbansal13.identity_service.models.UserRole;
import com.vbansal13.identity_service.payload.request.SignupRequest;
import com.vbansal13.identity_service.repository.UserRepository;
import com.vbansal13.identity_service.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Transactional
    public User registerUser(SignupRequest signUpRequest) {
        // Create new user's account
        User user = new User(
                signUpRequest.getName(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getPhoneNumber());

        Set<String> strRoles = signUpRequest.getRole();
        Set<UserRole> roles = new HashSet<>();

        if (CollectionUtils.isEmpty(strRoles)) {
            UserRole userRole = roleRepository.findByRole(RoleType.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        UserRole adminRole = roleRepository.findByRole(RoleType.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        UserRole modRole = roleRepository.findByRole(RoleType.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        UserRole userRole = roleRepository.findByRole(RoleType.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }
}
