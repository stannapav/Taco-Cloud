package org.example.tacocloud.Controllers;

import jakarta.validation.Valid;
import org.example.tacocloud.JdbcRepository.JdbsIngredientRepository;
import org.example.tacocloud.Repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
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
    private final IngredientRepository ingredientRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model){
        Iterable<Ingredient> ingredients = ingredientRepository.findAll();

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
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
        if(errors.hasErrors()) {
            return "redirect:/orders/current";
        }
        
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
