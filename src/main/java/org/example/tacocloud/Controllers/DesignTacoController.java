package org.example.tacocloud.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.tacocloud.Models.Taco;
import org.example.tacocloud.Models.Ingredient;
import org.example.tacocloud.Models.Ingredient.Type;
import org.example.tacocloud.Models.TacoOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    @ModelAttribute(name = "ingridients")
    public void addIngredientsToModel(Model model){
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),       
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),       
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),       
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),       
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),       
                new Ingredient("CHED", "Cheddar", Type.CHEESE),       
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa",Type.SAUCE),       
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE));

        Type[] types = Ingredient.Type.values();
        
        for (Type type : types) {       
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "tacoOrder")   
    public TacoOrder order() {     
        return new TacoOrder();   
    }     
    
    @ModelAttribute(name = "taco")  
    public Taco taco() {     
        return new Taco();   
    }
    
    @GetMapping   
    public String showDesignForm() {     
        return "design";  
    }
    
    @PostMapping
    public String processTaco(Taco taco, @ModelAttribute TacoOrder tacoOrder){
        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);
        
        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {     
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());   
    }
}
