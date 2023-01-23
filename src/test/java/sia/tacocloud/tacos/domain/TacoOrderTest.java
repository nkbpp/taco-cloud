package sia.tacocloud.tacos.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TacoOrderTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

    private Taco taco = new Taco(
            "TestName",
            List.of(new IngredientRef("XXX"))
    );

    @Test
    public void whenAllAcceptable() {
        TacoOrder tacoOrder = new TacoOrder(
                "Delivery",
                "Street",
                "City",
                "01",
                "Zip",

                "4111111111111111",
                "01/22",
                "012",
                new ArrayList<>(List.of(taco))
        );

        Set<ConstraintViolation<TacoOrder>> violations = validator.validate(tacoOrder);

        assertThat(violations).isEmpty();
    }

    @Test
    public void whenAllNull() {
        TacoOrder tacoOrder = new TacoOrder();
        tacoOrder.tacos = null;

        Set<ConstraintViolation<TacoOrder>> violations = validator.validate(tacoOrder);

        assertThat(violations.size()).isEqualTo(9);
        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("deliveryName") &&
                                testObjectConstraintViolation.getMessage()
                                        .equals("Delivery name is required"));
        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("deliveryStreet") &&
                                testObjectConstraintViolation.getMessage()
                                        .equals("Street is required"));
        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("deliveryCity") &&
                                testObjectConstraintViolation.getMessage()
                                        .equals("City is required"));
        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("deliveryState") &&
                                testObjectConstraintViolation.getMessage()
                                        .equals("State is required"));
        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("deliveryZip") &&
                                testObjectConstraintViolation.getMessage()
                                        .equals("Zip code is required"));

        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("ccNumber") &&
                                testObjectConstraintViolation.getMessage()
                                        .contains("null"));
        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("ccExpiration") &&
                                testObjectConstraintViolation.getMessage()
                                        .contains("null"));
        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("ccCVV") &&
                                testObjectConstraintViolation.getMessage()
                                        .contains("null"));

        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("tacos") &&
                                testObjectConstraintViolation.getMessage()
                                        .contains("null"));
    }

    @Test
    public void whenAllInvalid() {

        TacoOrder tacoOrder = new TacoOrder(
                "",
                "",
                "",
                "",
                "",

                "4",
                "21/22",
                "01",
                new ArrayList<>(List.of())
        );

        Set<ConstraintViolation<TacoOrder>> violations = validator.validate(tacoOrder);

        assertThat(violations.size()).isEqualTo(9);
        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("deliveryName") &&
                                testObjectConstraintViolation.getMessage()
                                        .equals("Delivery name is required"));
        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("deliveryStreet") &&
                                testObjectConstraintViolation.getMessage()
                                        .equals("Street is required"));
        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("deliveryCity") &&
                                testObjectConstraintViolation.getMessage()
                                        .equals("City is required"));
        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("deliveryState") &&
                                testObjectConstraintViolation.getMessage()
                                        .equals("State is required"));
        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("deliveryZip") &&
                                testObjectConstraintViolation.getMessage()
                                        .equals("Zip code is required"));

        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("ccNumber") &&
                                testObjectConstraintViolation.getMessage()
                                        .contains("Not a valid credit card number"));
        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("ccExpiration") &&
                                testObjectConstraintViolation.getMessage()
                                        .contains("Must be formatted MM/YY"));
        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("ccCVV") &&
                                testObjectConstraintViolation.getMessage()
                                        .contains("Invalid CVV"));

        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("tacos") &&
                                testObjectConstraintViolation.getMessage()
                                        .contains("You must choose at least 1 taco"));
    }
}