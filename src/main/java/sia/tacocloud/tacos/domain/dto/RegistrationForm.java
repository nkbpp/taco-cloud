package sia.tacocloud.tacos.domain.dto;

public record RegistrationForm(
        String username,
        String password,
        String fullname,
        String street,
        String city,
        String state,
        String zip,
        String phone
) {
}
