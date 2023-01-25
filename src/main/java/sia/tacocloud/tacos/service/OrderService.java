package sia.tacocloud.tacos.service;

import org.springframework.stereotype.Service;
import sia.tacocloud.tacos.domain.TacoOrder;
import sia.tacocloud.tacos.repository.OrderRepository;

@Service

public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public TacoOrder findById(Long id) {

        TacoOrder ttt = repository.findById(id).orElse(null);

        return repository.findById(id).orElse(null);
    }

}
