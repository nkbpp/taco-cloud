package sia.tacocloud.tacos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TacoOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    /*@Size(min = 3, max = 3, message = "Invalid CVV")
    @Digits(integer = 3,
            fraction = 0, //дробная часть
            message = "Invalid CVV")*/
    @NotNull
    @Pattern(regexp = "^(\\d{3})$",
            message = "Invalid CVV")
    private String ccCVV;

    private LocalDateTime placedAt = LocalDateTime.now();

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 taco")
    @OneToMany(cascade = CascadeType.ALL)
    List<Taco> tacos = new ArrayList<>();

    @ManyToOne
    private MyUser user;

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

    public TacoOrder(String deliveryName, String deliveryStreet, String deliveryCity, String deliveryState, String deliveryZip, String ccNumber, String ccExpiration, String ccCVV, List<Taco> tacos) {
        this.deliveryName = deliveryName;
        this.deliveryStreet = deliveryStreet;
        this.deliveryCity = deliveryCity;
        this.deliveryState = deliveryState;
        this.deliveryZip = deliveryZip;
        this.ccNumber = ccNumber;
        this.ccExpiration = ccExpiration;
        this.ccCVV = ccCVV;
        this.tacos = tacos;
    }

}