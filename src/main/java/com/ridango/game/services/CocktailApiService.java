package com.ridango.game.services;

import com.ridango.game.models.Cocktail;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class CocktailApiService {

    private static final String API_URL = "https://www.thecocktaildb.com/api/json/v1/1/";

    public Cocktail getRandomCocktail() throws Exception {
        String url = API_URL + "random.php";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        JSONObject cocktailJson = jsonResponse.getJSONArray("drinks").getJSONObject(0);

        Cocktail cocktail = new Cocktail();
        cocktail.setName(cocktailJson.getString("strDrink"));
        cocktail.setInstructions(cocktailJson.getString("strInstructions"));
        cocktail.setCategory(cocktailJson.getString("strCategory"));
        cocktail.setGlass(cocktailJson.getString("strGlass"));

        List<String> ingredients = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            String ingredient = cocktailJson.optString("strIngredient" + i, null);
            if (ingredient != null && !ingredient.isEmpty()) {
                ingredients.add(ingredient);
            }
        }
        cocktail.setIngredients(ingredients);

        cocktail.setPicture(cocktailJson.getString("strDrinkThumb"));

        return cocktail;
    }
}
