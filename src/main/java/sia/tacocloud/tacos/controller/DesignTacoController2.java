package sia.tacocloud.tacos.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.tacos.domain.Ingredient;
import sia.tacocloud.tacos.domain.Taco;
import sia.tacocloud.tacos.domain.TacoOrder;
import sia.tacocloud.tacos.domain.Type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*@Slf4j
@Controller
@RequestMapping("/design")*/
public class DesignTacoController2 {


/*    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @PostMapping
    public String processTaco(Taco taco,
                              @ModelAttribute TacoOrder tacoOrder,
                              Model model,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);
        return "redirect:/orders/current";
    }*/


}
