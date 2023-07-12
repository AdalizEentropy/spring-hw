package ru.nspk.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nspk.user.dto.NewUserDto;
import ru.nspk.user.model.UserRole;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(NewUserDto newUserDto) {
        ((JdbcUserDetailsManager) userDetailsService)
                .createUser(
                        User.builder()
                                .username(newUserDto.getLogin())
                                .password(passwordEncoder.encode(newUserDto.getPassword()))
                                .roles(
                                        newUserDto.getRole().stream()
                                                .map(UserRole::name)
                                                .toArray(String[]::new))
                                .build());
    }
}
