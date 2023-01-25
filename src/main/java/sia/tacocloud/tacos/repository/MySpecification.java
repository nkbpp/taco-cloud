package sia.tacocloud.tacos.repository;

public interface MySpecification<T> {
    boolean specified(T t);
}
