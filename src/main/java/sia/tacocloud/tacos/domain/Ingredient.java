package sia.tacocloud.tacos.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(
        access = AccessLevel.PRIVATE,
        force = true //все final поля инициализируются 0 / false / null
)
public class Ingredient {

    @Id
    private String id;
    private String name;
    private Type type;
    
}
