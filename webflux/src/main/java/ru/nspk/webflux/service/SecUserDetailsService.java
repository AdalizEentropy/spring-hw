package ru.nspk.webflux.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
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
        Mono<List<Authority>> authoritiesMono =
                authorityReactiveRepository.findByUsername(username).collectList();
        Mono<User> userMono = userReactiveRepository.findByUsername(username);
        return userMono.zipWith(
                authoritiesMono,
                (user1, authorities) ->
                        new User(
                                user1.getUsername(),
                                user1.getPassword(),
                                user1.isEnabled(),
                                authorities));
    }
}
