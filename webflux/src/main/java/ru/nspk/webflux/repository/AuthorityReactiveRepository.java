package ru.nspk.webflux.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.nspk.webflux.model.Role;

@Repository
public interface AuthorityReactiveRepository extends ReactiveCrudRepository<Role, String> {

    Flux<Role> findByUsername(String username);
}
