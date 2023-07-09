package ru.nspk.webflux.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.nspk.webflux.model.Authority;
import ru.nspk.webflux.model.User;
import ru.nspk.webflux.repository.AuthorityReactiveRepository;
import ru.nspk.webflux.repository.UserReactiveRepository;

@Service
@RequiredArgsConstructor
public class SecUserDetailsService implements ReactiveUserDetailsService {
    private final UserReactiveRepository userReactiveRepository;
    private final AuthorityReactiveRepository authorityReactiveRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Mono<User> user = userReactiveRepository.findByUsername(username);
        Flux<Authority> authorities = authorityReactiveRepository.findByUsername(username);
        return user.cast(UserDetails.class);
    }
}
