package com.example.smartpantrymanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class RecipeDAO {

    private final DatabaseHelper dbHelper;

    public RecipeDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Get all recipes
    public List<RecipeItem> getAllRecipes() {

        List<RecipeItem> recipes = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_RECIPES,
                null,
                null,
                null,
                null,
                null,
                DatabaseHelper.COL_RECIPE_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_RECIPE_ID
                        )
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_RECIPE_NAME
                        )
                );

                String instructions = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_RECIPE_INSTRUCTIONS
                        )
                );

                recipes.add(
                        new RecipeItem(id, name, instructions)
                );

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recipes;
    }

    // Get one recipe by ID
    public RecipeItem getRecipeById(int recipeId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_RECIPES,
                null,
                DatabaseHelper.COL_RECIPE_ID + "=?",
                new String[]{String.valueOf(recipeId)},
                null,
                null,
                null
        );

        RecipeItem recipe = null;

        if (cursor.moveToFirst()) {

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            DatabaseHelper.COL_RECIPE_NAME
                    )
            );

            String instructions = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            DatabaseHelper.COL_RECIPE_INSTRUCTIONS
                    )
            );

            recipe = new RecipeItem(
                    recipeId,
                    name,
                    instructions
            );
        }

        cursor.close();
        db.close();

        return recipe;
    }
    // Get all ingredients required for a recipe
    public List<RecipeIngredient> getIngredientsForRecipe(int recipeId) {

        List<RecipeIngredient> ingredients = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_RECIPE_INGREDIENTS,
                null,
                DatabaseHelper.COL_INGREDIENT_RECIPE_ID + "=?",
                new String[]{String.valueOf(recipeId)},
                null,
                null,
                DatabaseHelper.COL_INGREDIENT_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_INGREDIENT_ID
                        )
                );

                int recipeIdFromDatabase = cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_INGREDIENT_RECIPE_ID
                        )
                );

                String ingredientName = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_INGREDIENT_NAME
                        )
                );

                double requiredQuantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_INGREDIENT_QUANTITY
                        )
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_INGREDIENT_UNIT
                        )
                );

                ingredients.add(
                        new RecipeIngredient(
                                id,
                                recipeIdFromDatabase,
                                ingredientName,
                                requiredQuantity,
                                unit
                        )
                );

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ingredients;
    }
}