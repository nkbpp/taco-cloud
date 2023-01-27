package sia.tacocloud.tacos.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sia.tacocloud.tacos.domain.dto.RegistrationForm;

import java.util.function.Function;

@Component
public class UserRequestMapper implements Function<RegistrationForm, MyUser> {

    private final PasswordEncoder passwordEncoder;

    public UserRequestMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public MyUser apply(RegistrationForm registrationForm) {
        return new MyUser(
                null,
                registrationForm.username().trim(),
                passwordEncoder.encode(registrationForm.password().trim()),
                registrationForm.fullname(),
                registrationForm.street(),
                registrationForm.city(),
                registrationForm.state(),
                registrationForm.zip(),
                registrationForm.phone()
        );
    }

}
