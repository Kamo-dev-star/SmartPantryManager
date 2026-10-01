package com.example.smartpantrymanager;

import java.util.List;
import java.util.Locale;

public class RecipeMatchHelper {

    public static boolean canMakeRecipe(
            RecipeItem recipe,
            List<RecipeIngredient> recipeIngredients,
            List<PantryItem> pantryItems) {

        for (RecipeIngredient required : recipeIngredients) {

            boolean ingredientFound = false;

            for (PantryItem pantry : pantryItems) {

                if (normaliseName(pantry.getName())
                        .equals(normaliseName(required.getIngredientName()))) {

                    double pantryQuantity = convertToBaseUnit(
                            pantry.getQuantity(),
                            pantry.getUnit()
                    );

                    double requiredQuantity = convertToBaseUnit(
                            required.getRequiredQuantity(),
                            required.getUnit()
                    );

                    if (pantryQuantity >= requiredQuantity) {
                        ingredientFound = true;
                        break;
                    }
                }
            }

            if (!ingredientFound) {
                return false;
            }
        }

        return true;
    }

    private static String normaliseName(String name) {

        String value = name.trim().toLowerCase(Locale.ROOT);

        if (value.endsWith("ies")) {
            value = value.substring(0, value.length() - 3) + "y";
        } else if (value.endsWith("es")) {
            value = value.substring(0, value.length() - 2);
        } else if (value.endsWith("s")) {
            value = value.substring(0, value.length() - 1);
        }

        return value;
    }

    private static double convertToBaseUnit(double quantity, String unit) {

        String value = unit.trim().toLowerCase(Locale.ROOT);

        switch (value) {

            case "kg":
            case "kilogram":
            case "kilograms":
                return quantity * 1000;

            case "g":
            case "gram":
            case "grams":
                return quantity;

            case "l":
            case "litre":
            case "litres":
            case "liter":
            case "liters":
                return quantity * 1000;

            case "ml":
            case "millilitre":
            case "millilitres":
            case "milliliter":
            case "milliliters":
                return quantity;

            case "piece":
            case "pieces":
            case "pc":
            case "pcs":
                return quantity;

            default:
                return quantity;
        }
    }
}