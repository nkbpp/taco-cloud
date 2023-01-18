package sia.tacocloud.tacos.repositoryJdbsTemplate;

import org.springframework.asm.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sia.tacocloud.tacos.domain.IngredientRef;
import sia.tacocloud.tacos.domain.Taco;
import sia.tacocloud.tacos.domain.TacoOrder;
import sia.tacocloud.tacos.repository.OrderRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {
        order.setPlacedAt(LocalDateTime.now());
        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(
                        "insert into Taco_Order "
                                + "(delivery_name, delivery_street, delivery_city, "
                                + "delivery_state, delivery_zip, cc_number, "
                                + "cc_expiration, cc_cvv, placed_at) "
                                + "values (?,?,?,?,?,?,?,?,?)",
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
                );
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                order.getDeliveryName(),
                                order.getDeliveryStreet(),
                                order.getDeliveryCity(),
                                order.getDeliveryState(),
                                order.getDeliveryZip(),
                                order.getCcNumber(),
                                order.getCcExpiration(),
                                order.getCcCVV(),
                                order.getPlacedAt()));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        int i = 0;
        for (Taco taco : tacos) {
            saveTaco(orderId, i++, taco);
        }
        return order;
    }

    private long saveTaco(Long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(LocalDateTime.now());
        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(
                        "insert into Taco "
                                + "(name, created_at, taco_order, taco_order_key) "
                                + "values (?, ?, ?, ?)",
                        Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
                );
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                taco.getName(),
                                taco.getCreatedAt(),
                                orderId,
                                orderKey));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        long tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);
        saveIngredientRefs(tacoId, taco.getIngredients());
        return tacoId;
    }

    private void saveIngredientRefs(
            long tacoId, List<IngredientRef> ingredientRefs) {
        int key = 0;
        for (IngredientRef ingredientRef : ingredientRefs) {
            jdbcTemplate.update(
                    "insert into Ingredient_Ref (ingredient, taco, taco_key) "
                            + "values (?, ?, ?)",
                    ingredientRef.ingredient(), tacoId, key++);
        }
    }

    @Override
    public Optional<TacoOrder> findById(Long id) {
        try {
            TacoOrder order = jdbcTemplate.queryForObject(
                    "select id, delivery_name, delivery_street, delivery_city, "
                            + "delivery_state, delivery_zip, cc_number, cc_expiration, "
                            + "cc_cvv, placed_at from Taco_Order where id=?",
                    this::mapRowToTacoOrder,
                    id);
            return Optional.of(order);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    private List<Taco> findTacosByOrderId(long orderId) {
        return jdbcTemplate.query(
                "select id, name, created_at from Taco "
                        + "where taco_order=? order by taco_order_key",
                (row, rowNum) -> {
                    Taco taco = new Taco();
                    taco.setId(row.getLong("id"));
                    taco.setName(row.getString("name"));
                    taco.setCreatedAt(row.getTimestamp("created_at").toLocalDateTime());
                    taco.setIngredients(findIngredientsByTacoId(row.getLong("id")));
                    return taco;
                },
                orderId);
    }

    private List<IngredientRef> findIngredientsByTacoId(long tacoId) {
        return jdbcTemplate.query(
                "select ingredient from Ingredient_Ref "
                        + "where taco = ? order by taco_key",
                (row, rowNum) -> new IngredientRef(row.getString("ingredient")),
                tacoId);
    }


    @Override
    public List<TacoOrder> findAll() {
        return jdbcTemplate.query(
                "select id, delivery_name, delivery_street, delivery_city, "
                        + "delivery_state, delivery_zip, cc_number, cc_expiration, "
                        + "cc_cvv, placed_at from Taco_Order where id=?",
                this::mapRowToTacoOrder);
    }

    private TacoOrder mapRowToTacoOrder(ResultSet row, int rowNum)
            throws SQLException {
        return new TacoOrder(
                row.getLong("id"),
                row.getString("delivery_name"),
                row.getString("delivery_street"),
                row.getString("delivery_city"),
                row.getString("delivery_state"),
                row.getString("delivery_zip"),
                row.getString("cc_number"),
                row.getString("cc_expiration"),
                row.getString("cc_cvv"),
                row.getTimestamp("placed_at").toLocalDateTime(),
                findTacosByOrderId(row.getLong("id"))
        );
    }

}
