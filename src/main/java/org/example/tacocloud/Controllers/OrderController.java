package org.example.tacocloud.Controllers;

import jakarta.validation.Valid;
import org.springframework.validation.Errors;
import lombok.extern.slf4j.Slf4j;
import org.example.tacocloud.Models.TacoOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    @GetMapping("/current")
    public String orderForm(){
        return "orderForm";
    }
    
    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus){
        if (errors.hasErrors()) {
            return "orderForm";
        }
        log.info("error, {}", errors);
        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();
        
        return "redirect:/";
    }
}
