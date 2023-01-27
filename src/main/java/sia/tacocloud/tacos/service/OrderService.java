package sia.tacocloud.tacos.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import sia.tacocloud.tacos.domain.TacoOrder;
import sia.tacocloud.tacos.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllOrders() {
        repository.deleteAll();
    }

    public void save(TacoOrder tacoOrder) {
        repository.save(tacoOrder);
    }

}
