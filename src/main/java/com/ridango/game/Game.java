package com.ridango.game;

import com.ridango.game.models.Cocktail;
import com.ridango.game.services.CocktailApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game {
    private CocktailApiService apiService = new CocktailApiService();
    private int score = 0;
    private int attemptsLeft = 5;
    private Cocktail currentCocktail;
    private int incorrectAttempts = 0;
    private String hiddenName;

    public void start() throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean play = true;

        System.out.println("Welcome to the cocktail name guessing game!");

        while (play) {
            currentCocktail = apiService.getRandomCocktail();
            hiddenName = createHiddenName(currentCocktail.getName());

            System.out.println("Guess the cocktail: " + hiddenName);
            System.out.println("Hint 1, Category: " + currentCocktail.getCategory());

            incorrectAttempts = 0;

            for (int i = 0; i < 5; i++) {
                System.out.println("Attempt " + (i + 1) + ": Enter your guess:");
                String guess = scanner.nextLine();

                if (guess.equalsIgnoreCase(currentCocktail.getName())) {
                    System.out.println("Correct! The cocktail name is " + currentCocktail.getName());
                    score += attemptsLeft;
                    System.out.println("Your score is " + score);
                    break;
                } else {
                    revealLetter(incorrectAttempts);
                    revealHint(incorrectAttempts);
                    incorrectAttempts++;
                    attemptsLeft--;
                    if (attemptsLeft == 0) {
                        System.out.println("You ran out of attempts. The cocktail name is " + currentCocktail.getName());
                        break;
                    }
                }
            }

            System.out.println("Do you want to play again? (y/n)");
            String response = scanner.nextLine();
            if (!response.equalsIgnoreCase("y")) {
                play = false;
                System.out.println("Game over! Your final score is: " + score);
            }

            attemptsLeft = 5;
        }
    }

    private String createHiddenName(String cocktailName) {
        return cocktailName.replaceAll("[^ ]", "_");
    }

    private void revealLetter(int attempt) {
        String cocktailName = currentCocktail.getName();
        StringBuilder hiddenNameBuilder = new StringBuilder(hiddenName);
        char[] nameChars = cocktailName.toCharArray();
        char[] hiddenChars = hiddenName.toCharArray();

        int numLettersToReveal = (cocktailName.length() > 14) ? 2 : 1;

        List<Integer> indicesToReveal = new ArrayList<>();
        for (int i = 0; i < nameChars.length; i++) {
            if (hiddenChars[i] == '_' && nameChars[i] != ' ') {
                indicesToReveal.add(i);
            }
        }

        Collections.shuffle(indicesToReveal);
        int revealed = 0;
        for (int index : indicesToReveal) {
            if (revealed < numLettersToReveal) {
                hiddenNameBuilder.setCharAt(index, nameChars[index]);
                revealed++;
            } else {
                break;
            }
        }

        hiddenName = hiddenNameBuilder.toString();
        System.out.println("Guess the cocktail: " + hiddenName);
    }


    private void revealHint(int attempt) {
        if (attempt == 0) {
            System.out.println("Hint 2, Glass - " + currentCocktail.getGlass());
        } else if (attempt == 1) {
            System.out.println("Hint 3, Ingredients - " + currentCocktail.getIngredients());
        } else if (attempt == 2) {
            System.out.println("Hint 4, Instructions - " + currentCocktail.getInstructions());
        } else if (attempt == 3) {
            System.out.println("Hint 5, Picture - " + currentCocktail.getPicture());
        }
    }
}
