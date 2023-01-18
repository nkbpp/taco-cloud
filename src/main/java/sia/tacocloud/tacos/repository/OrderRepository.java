package sia.tacocloud.tacos.repository;

import sia.tacocloud.tacos.domain.TacoOrder;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);

    Optional<TacoOrder> findById(Long id);

    List<TacoOrder> findAll();
}
