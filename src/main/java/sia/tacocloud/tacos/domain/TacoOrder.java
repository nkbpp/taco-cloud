package sia.tacocloud.tacos.domain;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class TacoOrder {

    @Id
    private Long id;

    @NotBlank(message = "Delivery name is required")
    private String deliveryName;
    @NotBlank(message = "Street is required")
    private String deliveryStreet;
    @NotBlank(message = "City is required")
    private String deliveryCity;
    @NotBlank(message = "State is required")
    private String deliveryState;
    @NotBlank(message = "Zip code is required")
    private String deliveryZip;

    @NotNull
    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;
    @NotNull
    @Pattern(regexp = "^(0[1-9]|1[0-2])(/)([2-9]\\d)$",
            message = "Must be formatted MM/YY")
    private String ccExpiration;
    @NotNull
    @Size(min = 3, max = 3, message = "Invalid CVV")
    @Digits(integer = 3,
            fraction = 0, //дробная часть
            message = "Invalid CVV")
    private String ccCVV;

    private LocalDateTime placedAt = LocalDateTime.now();

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 taco")
    List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }

    public TacoOrder(String deliveryName, String deliveryStreet, String deliveryCity, String deliveryState, String deliveryZip, String ccNumber, String ccExpiration, String ccCVV) {
        this.deliveryName = deliveryName;
        this.deliveryStreet = deliveryStreet;
        this.deliveryCity = deliveryCity;
        this.deliveryState = deliveryState;
        this.deliveryZip = deliveryZip;
        this.ccNumber = ccNumber;
        this.ccExpiration = ccExpiration;
        this.ccCVV = ccCVV;
    }

}