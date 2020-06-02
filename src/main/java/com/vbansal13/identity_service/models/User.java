package com.vbansal13.identity_service.models;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users",
    uniqueConstraints = {
            @UniqueConstraint(columnNames = "email")
    })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    @Email(message = "*Please provide a valid Email.")
    @NotEmpty(message = "*Please provide an Email.")
    private String email;

    @Column(name = "password")
    @Size(min = 8, message = "*Your password must have at least 8 characters", max = 100)
    @NotEmpty(message = "*Please provide your password.")
    private String password;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "create_date")
    private Date creationDate;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> roles;


    //@OneToMany(mappedBy = "userEmail")
    //private Set<Membership> membership;

    public User() {

    }

    public User(String name,
                String email,
                String password,
                String phoneNumber
                ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.active = true;
        this.creationDate = new Date();
    }

    public User(String name,
                String email,
                String password,
                String phoneNumber,
                Boolean active, Date creationDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.active = active;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", creationDate=" + creationDate +
                '}';
    }
}
