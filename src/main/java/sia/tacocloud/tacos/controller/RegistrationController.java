package sia.tacocloud.tacos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sia.tacocloud.tacos.domain.UserRequestMapper;
import sia.tacocloud.tacos.domain.dto.RegistrationForm;
import sia.tacocloud.tacos.repository.MyUserRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final MyUserRepository myUserRepository;
    private final UserRequestMapper userRequestMapper;

    public RegistrationController(
            MyUserRepository myUserRepository,
            UserRequestMapper userRequestMapper
    ) {
        this.myUserRepository = myUserRepository;
        this.userRequestMapper = userRequestMapper;
    }

    //todo лень заполнять
    @ModelAttribute(name = "registration")
    public RegistrationForm registrationForm() {
        return new RegistrationForm(
                "woody",
                "password",
                "Woody Woodpecker",
                "Street",
                "City",
                "01",
                "Zip",
                "85555555555"
        );
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {
        myUserRepository.save(userRequestMapper.apply(form));
        return "redirect:/login";
    }

}
