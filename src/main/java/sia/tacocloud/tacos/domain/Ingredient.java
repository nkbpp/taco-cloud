package sia.tacocloud.tacos.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Table
public class Ingredient {

    @Id
    private String id;
    private String name;
    private Type type;

}
