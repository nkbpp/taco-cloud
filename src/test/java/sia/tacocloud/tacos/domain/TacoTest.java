package sia.tacocloud.tacos.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class TacoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

    @Test
    public void whenAllAcceptable() {
        Taco taco = new Taco(
                "TestName",
                List.of(new Ingredient("XXX", "sdf", Type.CHEESE))
        );

        Set<ConstraintViolation<Taco>> violations = validator.validate(taco);

        assertThat(violations).isEmpty();
    }

    @Test
    public void whenAllNull() {
        Taco taco = new Taco(
                null,
                null
        );


        Set<ConstraintViolation<Taco>> violations = validator.validate(taco);

        assertThat(violations.size()).isEqualTo(2);
        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("name") &&
                                testObjectConstraintViolation.getMessage()
                                        .contains("null"));
        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("ingredients") &&
                                testObjectConstraintViolation.getMessage()
                                        .contains("null"));
    }

    @Test
    public void whenAllInvalid() {
        Taco taco = new Taco(
                "4",
                List.of()
        );

        Set<ConstraintViolation<Taco>> violations = validator.validate(taco);

        assertThat(violations.size()).isEqualTo(2);
        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("name") &&
                                testObjectConstraintViolation.getMessage()
                                        .equals("Name must be at least 5 characters long"));
        assertThat(violations).anyMatch(
                testObjectConstraintViolation ->
                        testObjectConstraintViolation.getPropertyPath()
                                .toString().equals("ingredients") &&
                                testObjectConstraintViolation.getMessage()
                                        .equals("You must choose at least 1 ingredient"));
    }

}