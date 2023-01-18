package sia.tacocloud.tacos.repository;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.tacos.domain.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
