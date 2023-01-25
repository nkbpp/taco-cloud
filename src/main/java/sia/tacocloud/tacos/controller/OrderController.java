package sia.tacocloud.tacos.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import sia.tacocloud.tacos.domain.TacoOrder;
import sia.tacocloud.tacos.repository.OrderRepository;
import sia.tacocloud.tacos.service.OrderService;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {


    private final OrderRepository orderRepository;

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors,
                               SessionStatus sessionStatus //Уничтожение объектов, объявленных в сессии
    ) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        orderRepository.save(order);
        TacoOrder t = orderService.findById(1L);
        /*уничтожает объекты объявленные в @SessionAttributes(..) */
        sessionStatus.setComplete();
        return "redirect:/";
    }

}