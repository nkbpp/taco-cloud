package sia.tacocloud.tacos.repository;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.tacos.domain.MyUser;

import java.util.Optional;

public interface MyUserRepository extends CrudRepository<MyUser, Long> {

    Optional<MyUser> findByUsername(String username);

}
