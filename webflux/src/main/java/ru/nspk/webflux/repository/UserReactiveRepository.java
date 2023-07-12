package ru.nspk.webflux.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.nspk.webflux.model.User;

@Repository
public interface UserReactiveRepository extends ReactiveCrudRepository<User, String> {

    Mono<User> findByUsername(String username);
}
