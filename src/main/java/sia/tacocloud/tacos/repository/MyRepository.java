package sia.tacocloud.tacos.repository;

import java.util.List;
import java.util.Optional;

public interface MyRepository<T, I> {

    void add(T t);

    void addAll(List<T> t);

    void update(T t);

    T save(T t);

    void delete(T t);

    void deleteAll(List<T> t);

    void deleteAll();

    List<T> query(MySpecification specification);

    Optional<T> findBy(MySpecification specification);

    Optional<T> findById(I id);

    List<T> findAll();

}
