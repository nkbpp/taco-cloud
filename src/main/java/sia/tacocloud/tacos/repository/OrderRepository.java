package sia.tacocloud.tacos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sia.tacocloud.tacos.domain.TacoOrder;

@Repository
public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
}
