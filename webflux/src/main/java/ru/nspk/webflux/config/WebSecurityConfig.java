package ru.nspk.webflux.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import ru.nspk.webflux.repository.AuthorityReactiveRepository;
import ru.nspk.webflux.repository.UserReactiveRepository;
import ru.nspk.webflux.service.SecUserDetailsService;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final UserReactiveRepository userReactiveRepository;
    private final AuthorityReactiveRepository authorityReactiveRepository;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity
                .csrf()
                .disable()
                .authorizeExchange(
                        exchanges ->
                                exchanges
                                        .pathMatchers("login", "error")
                                        .permitAll()
                                        .anyExchange()
                                        .authenticated())
                .httpBasic();

        return httpSecurity.build();
    }

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService() {
        return new SecUserDetailsService(userReactiveRepository, authorityReactiveRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
