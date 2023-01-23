package sia.tacocloud.tacos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    @ManyToMany
    private List<Ingredient> ingredients = new ArrayList<>();

    private LocalDateTime createdAt = LocalDateTime.now();

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public Taco(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }
}