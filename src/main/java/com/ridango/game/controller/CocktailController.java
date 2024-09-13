package com.ridango.game.controller;

import com.ridango.game.models.Cocktail;
import com.ridango.game.services.CocktailApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cocktails")
public class CocktailController {

    @Autowired
    private CocktailApiService cocktailApiService;

    @GetMapping
    public List<Cocktail> getAllCocktails() throws Exception {
        return List.of(cocktailApiService.getRandomCocktail());
    }

    @GetMapping("/{id}")
    public Cocktail getCocktailById(@PathVariable Long id) throws Exception {
        return cocktailApiService.getRandomCocktail();
    }
}
