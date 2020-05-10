package com.katafoni.filemanager.security.auth;

import com.katafoni.filemanager.security.user.User;
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

    ApplicationUser(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.isAccountNonExpired = user.isAccountNonExpired();
        this.isAccountNonLocked = user.isAccountNonLocked();
        this.isCredentialsNonExpired = user.isCredentialsNonExpired();
        this.isEnabled = user.isEnabled();
        this.grantedAuthorities = user.getApplicationUserRole().getGrantedAuthorities();
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
