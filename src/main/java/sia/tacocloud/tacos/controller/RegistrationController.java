package sia.tacocloud.tacos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sia.tacocloud.tacos.domain.UserRequestMapper;
import sia.tacocloud.tacos.domain.dto.RegistrationForm;
import sia.tacocloud.tacos.repository.UserRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepository;
    private final UserRequestMapper userRequestMapper;

    public RegistrationController(
            UserRepository userRepository,
            UserRequestMapper userRequestMapper
    ) {
        this.userRepository = userRepository;
        this.userRequestMapper = userRequestMapper;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {
        userRepository.save(userRequestMapper.apply(form));
        return "redirect:/login";
    }

}
