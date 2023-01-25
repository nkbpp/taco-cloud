package sia.tacocloud.tacos.repository;

import org.springframework.stereotype.Repository;
import sia.tacocloud.tacos.domain.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public class IngredientRepository implements MyRepository<Ingredient, String> {

    private final IngredientRepositoryJpa repository;

    public IngredientRepository(IngredientRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public void update(Ingredient ingredient) {
        repository.save(ingredient);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        return repository.save(ingredient);
    }

    @Override
    public void delete(Ingredient ingredient) {
        repository.delete(ingredient);
    }

    @Override
    public void deleteAll(List<Ingredient> ingredients) {
        repository.deleteAll(ingredients);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public void add(Ingredient ingredient) {
        repository.save(ingredient);
    }

    @Override
    public void addAll(List<Ingredient> ingredients) {
        repository.saveAll(ingredients);
    }

    @Override
    public List<Ingredient> query(MySpecification specification) {
        List<Ingredient> lists = new ArrayList<>();
        repository.findAll().forEach(
                ingredient -> {
                    if (specification.specified(ingredient)) {
                        lists.add(ingredient);
                    }
                }
        );
        return lists;
    }

    @Override
    public Optional<Ingredient> findBy(MySpecification specification) {
        Ingredient ingredient = null;
        for (var i :
                repository.findAll()) {
            if (specification.specified(i)) {
                ingredient = i;
                break;
            }
        }
        return ingredient != null ? Optional.of(ingredient) : Optional.empty();
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Ingredient> findAll() {
        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .toList();
    }
}
