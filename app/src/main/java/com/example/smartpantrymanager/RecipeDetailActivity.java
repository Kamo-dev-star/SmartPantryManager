package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView txtRecipeName;
    private TextView txtRecipeIngredients;
    private TextView txtRecipeInstructions;

    private RecipeDAO recipeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);

        txtRecipeName = findViewById(R.id.txtRecipeName);
        txtRecipeIngredients = findViewById(R.id.txtRecipeIngredients);
        txtRecipeInstructions = findViewById(R.id.txtRecipeInstructions);

        recipeDAO = new RecipeDAO(this);

        int recipeId = getIntent().getIntExtra("recipe_id", -1);

        if (recipeId != -1) {
            loadRecipe(recipeId);
        }
    }

    private void loadRecipe(int recipeId) {

        RecipeItem recipe = recipeDAO.getRecipeById(recipeId);

        if (recipe == null) {
            return;
        }

        txtRecipeName.setText(recipe.getName());

        List<RecipeIngredient> ingredients =
                recipeDAO.getIngredientsForRecipe(recipeId);

        StringBuilder ingredientText = new StringBuilder();

        for (RecipeIngredient ingredient : ingredients) {

            ingredientText.append("• ")
                    .append(ingredient.getIngredientName())
                    .append(" - ")
                    .append(ingredient.getRequiredQuantity())
                    .append(" ")
                    .append(ingredient.getUnit())
                    .append("\n");
        }

        txtRecipeIngredients.setText(ingredientText.toString());

        txtRecipeInstructions.setText(
                recipe.getInstructions()
        );
    }
}