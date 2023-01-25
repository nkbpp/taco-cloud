package sia.tacocloud.tacos.repository;

import org.springframework.stereotype.Repository;
import sia.tacocloud.tacos.domain.TacoOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public class OrderRepository implements MyRepository<TacoOrder, Long> {

    private final OrderRepositoryJpa repository;

    public OrderRepository(OrderRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public void add(TacoOrder tacoOrder) {
        repository.save(tacoOrder);
    }

    @Override
    public void addAll(List<TacoOrder> t) {
        repository.saveAll(t);
    }

    @Override
    public void update(TacoOrder tacoOrder) {
        repository.save(tacoOrder);
    }

    @Override
    public TacoOrder save(TacoOrder tacoOrder) {
        return repository.save(tacoOrder);
    }

    @Override
    public void delete(TacoOrder tacoOrder) {
        repository.save(tacoOrder);
    }

    @Override
    public void deleteAll(List<TacoOrder> t) {
        repository.deleteAll(t);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public List<TacoOrder> query(MySpecification specification) {
        List<TacoOrder> tacoOrders = new ArrayList<>();
        repository.findAll().forEach(
                tacoOrder -> {
                    if (specification.specified(tacoOrder)) {
                        tacoOrders.add(tacoOrder);
                    }
                }
        );
        return tacoOrders;
    }

    @Override
    public Optional<TacoOrder> findBy(MySpecification specification) {
        TacoOrder tacoOrder = null;
        for (var order :
                repository.findAll()) {
            if (specification.specified(order)) {
                tacoOrder = order;
                break;
            }
        }
        return tacoOrder != null ? Optional.of(tacoOrder) : Optional.empty();
    }

    @Override
    public Optional<TacoOrder> findById(Long id) {
        return repository.findById(id);
    }

    @Override

    public List<TacoOrder> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).toList();
    }
}
