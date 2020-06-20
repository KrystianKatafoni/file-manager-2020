package com.katafoni.filemanager.common.security.auth;

import com.katafoni.filemanager.common.security.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationUser implements UserDetails {


    private Set<? extends GrantedAuthority> grantedAuthorities;
    private String username;
    private String password;
    private boolean isAccountNonLocked;
    private boolean isAccountNonExpired;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    public ApplicationUser(UserEntity userEntity) {
        this.username = userEntity.getEmail();
        this.password = userEntity.getPassword();
        this.isAccountNonExpired = userEntity.isAccountNonExpired();
        this.isAccountNonLocked = userEntity.isAccountNonLocked();
        this.isCredentialsNonExpired = userEntity.isCredentialsNonExpired();
        this.isEnabled = userEntity.isEnabled();
        this.grantedAuthorities = userEntity.getApplicationUserRole().getGrantedAuthorities();
    }
    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
