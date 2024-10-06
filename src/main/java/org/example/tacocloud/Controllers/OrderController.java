package org.example.tacocloud.Controllers;

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
    public String processOrder(@ModelAttribute TacoOrder order, SessionStatus sessionStatus){
        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();
        
        return "redirect:/";
    }
}
