package sia.tacocloud.tacos.repository;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.tacos.domain.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, String> {
}
