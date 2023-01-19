package sia.tacocloud.tacos.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
@AllArgsConstructor
@NoArgsConstructor(
        access = AccessLevel.PRIVATE,
        force = true //все final поля инициализируются 0 / false / null
)
public class Ingredient implements Persistable<String> {

    @Id
    private String id;
    private String name;
    private Type type;

    /*Если идентификатор создается на стороне приложения,
    то такой объект должен имплементировать интерфейс Persistable и реализовать метод isNew*/
    @Override
    public boolean isNew() {
        return true;
    }

}
