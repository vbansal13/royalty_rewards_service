package com.vbansal13.identity_service.models;



import lombok.*;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class UserRole {


    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleType role;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;




    public UserRole() {

    }

    public UserRole(RoleType name) {
        this.role = name;
    }

}
