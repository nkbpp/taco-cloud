package sia.tacocloud.tacos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sia.tacocloud.tacos.domain.Ingredient;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
