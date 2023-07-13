package ru.nspk.webflux.model;

import java.util.*;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table("users")
@Getter
public class User implements UserDetails {
    @Id private final String username;
    private final String password;
    private final boolean enabled;

    @MappedCollection(idColumn = "username")
    @Transient
    private final List<Role> roles;

    public User(String username, String password, boolean enabled, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    @PersistenceCreator
    public User(String username, String password, boolean enabled) {
        this(username, password, enabled, new ArrayList<>());
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
