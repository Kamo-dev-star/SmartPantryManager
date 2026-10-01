package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerSuggestedRecipes;
    private TextView txtNoRecipes;

    private RecipeAdapter adapter;
    private RecipeDAO recipeDAO;
    private PantryDAO pantryDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suggested_recipes);

        recyclerSuggestedRecipes =
                findViewById(R.id.recyclerSuggestedRecipes);

        txtNoRecipes =
                findViewById(R.id.txtNoRecipes);

        recipeDAO = new RecipeDAO(this);
        pantryDAO = new PantryDAO(this);

        recyclerSuggestedRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        adapter = new RecipeAdapter(
                new ArrayList<>(),
                recipe -> {
                    Intent intent = new Intent(
                            SuggestedRecipesActivity.this,
                            RecipeDetailActivity.class
                    );

                    intent.putExtra("recipe_id", recipe.getId());

                    startActivity(intent);
                }
        );

        recyclerSuggestedRecipes.setAdapter(adapter);

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        List<PantryItem> pantryItems =
                pantryDAO.getAllItems();

        List<RecipeItem> allRecipes =
                recipeDAO.getAllRecipes();

        List<RecipeItem> suggestedRecipes =
                new ArrayList<>();

        for (RecipeItem recipe : allRecipes) {

            List<RecipeIngredient> ingredients =
                    recipeDAO.getIngredientsForRecipe(
                            recipe.getId()
                    );

            if (RecipeMatchHelper.canMakeRecipe(
                    recipe,
                    ingredients,
                    pantryItems)) {

                suggestedRecipes.add(recipe);
            }
        }

        adapter.updateRecipes(suggestedRecipes);

        if (suggestedRecipes.isEmpty()) {

            txtNoRecipes.setVisibility(TextView.VISIBLE);
            recyclerSuggestedRecipes.setVisibility(
                    RecyclerView.GONE
            );

        } else {

            txtNoRecipes.setVisibility(TextView.GONE);
            recyclerSuggestedRecipes.setVisibility(
                    RecyclerView.VISIBLE
            );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (recipeDAO != null && pantryDAO != null) {
            loadSuggestedRecipes();
        }
    }
}