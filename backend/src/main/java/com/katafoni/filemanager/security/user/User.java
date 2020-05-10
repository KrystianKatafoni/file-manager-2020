package com.katafoni.filemanager.security.user;

import com.katafoni.filemanager.security.auth.ApplicationUserRole;
import com.katafoni.filemanager.security.validator.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    @ValidEmail(message = "Email is not valid")
    private String email;

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private ApplicationUserRole applicationUserRole;


    private boolean accountNonLocked;

    private boolean accountNonExpired;

    private boolean credentialsNonExpired;

    private boolean enabled;

}
