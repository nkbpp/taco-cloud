package sia.tacocloud.tacos.repository;

import sia.tacocloud.tacos.domain.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
