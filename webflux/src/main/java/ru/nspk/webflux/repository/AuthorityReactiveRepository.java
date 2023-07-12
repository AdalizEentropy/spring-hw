package ru.nspk.webflux.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.nspk.webflux.model.Authority;

@Repository
public interface AuthorityReactiveRepository extends ReactiveCrudRepository<Authority, String> {

    Flux<Authority> findByUsername(String username);
}
