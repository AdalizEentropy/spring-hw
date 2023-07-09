package ru.nspk.webflux.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;

@Table("authorities")
@Getter
@AllArgsConstructor
public class Authority implements GrantedAuthority {
    private final String username;
    private final String authority;
}
