package sia.tacocloud.tacos.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sia.tacocloud.tacos.domain.Ingredient;
import sia.tacocloud.tacos.domain.Type;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class IngredientRepositoryTest {

    @Autowired
    IngredientRepository ingredientRepo;

    @Test
    public void findByIdCorrect() {
        Optional<Ingredient> flto = ingredientRepo.findById("FLTO");
        assertThat(flto.isPresent()).isTrue();
        assertThat(flto.get()).isEqualTo(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP)
        );
    }

    @Test
    public void findByIdIncorrect() {
        Optional<Ingredient> xxxx = ingredientRepo.findById("XXXX");
        assertThat(xxxx.isEmpty()).isTrue();
    }

}