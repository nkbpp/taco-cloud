package sia.tacocloud.tacos.repository;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.tacos.domain.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
    
}
