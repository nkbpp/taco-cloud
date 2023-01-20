package sia.tacocloud.tacos.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import sia.tacocloud.tacos.domain.Ingredient;
import sia.tacocloud.tacos.domain.Taco;
import sia.tacocloud.tacos.domain.TacoOrder;
import sia.tacocloud.tacos.domain.Type;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//По умолчанию тесты с аннотацией @DataJdbcTest являются транзакционными и откатываются
// в конце каждого теста. Они также используют встроенную базу данных в памяти
// (заменяющую любой явный или обычно автоматически настраиваемый источник данных)
@DataJdbcTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepo;

    TacoOrder order;
    Taco taco1;
    Taco taco2;

    @BeforeEach
    public void before() {
        order = new TacoOrder();
        order.setDeliveryName("Test McTest");
        order.setDeliveryStreet("1234 Test Lane");
        order.setDeliveryCity("Testville");
        order.setDeliveryState("CO");
        order.setDeliveryZip("80123");
        order.setCcNumber("4111111111111111");
        order.setCcExpiration("10/23");
        order.setCcCVV("123");
        taco1 = new Taco();
        taco1.setName("Taco One");
        taco1.addIngredient(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        taco1.addIngredient(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
        taco1.addIngredient(new Ingredient("CHED", "Shredded Cheddar", Type.CHEESE));
        order.addTaco(taco1);
        taco2 = new Taco();
        taco2.setName("Taco Two");
        taco2.addIngredient(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
        taco2.addIngredient(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
        taco2.addIngredient(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
        order.addTaco(taco2);
        taco2.setCreatedAt(taco1.getCreatedAt());
    }

    @Test
    public void saveOrderWithTwoTacos() {

        TacoOrder savedOrder = orderRepo.save(order);
        assertThat(savedOrder.getId()).isNotNull();

        TacoOrder fetchedOrder = orderRepo.findById(savedOrder.getId()).get();
        List<Taco> tacos = fetchedOrder.getTacos();
        for (var t :
                tacos) {
            t.setCreatedAt(taco1.getCreatedAt());
        }
        
        assertThat(fetchedOrder.getDeliveryName()).isEqualTo("Test McTest");
        assertThat(fetchedOrder.getDeliveryStreet()).isEqualTo("1234 Test Lane");
        assertThat(fetchedOrder.getDeliveryCity()).isEqualTo("Testville");
        assertThat(fetchedOrder.getDeliveryState()).isEqualTo("CO");
        assertThat(fetchedOrder.getDeliveryZip()).isEqualTo("80123");
        assertThat(fetchedOrder.getCcNumber()).isEqualTo("4111111111111111");
        assertThat(fetchedOrder.getCcExpiration()).isEqualTo("10/23");
        assertThat(fetchedOrder.getCcCVV()).isEqualTo("123");
        assertThat(fetchedOrder.getPlacedAt().getSecond()).isEqualTo(savedOrder.getPlacedAt().getSecond());
        assertThat(tacos.size()).isEqualTo(2);
        assertThat(tacos).containsExactlyInAnyOrder(taco1, taco2); //содержит ровно в любом порядке
    }

}