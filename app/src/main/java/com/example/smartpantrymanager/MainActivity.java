package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPantry;
    private TextView txtEmptyPantry;
    private TextView txtPantryCount;
    private TextView txtExpiringCount;
    private Button btnAddIngredient;
    private BottomNavigationView bottomNavigation;

    private PantryDAO pantryDAO;
    private PantryAdapter pantryAdapter;
    private List<PantryItem> pantryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerPantry = findViewById(R.id.recyclerPantry);
        txtEmptyPantry = findViewById(R.id.txtEmptyPantry);
        txtPantryCount = findViewById(R.id.txtPantryCount);
        txtExpiringCount = findViewById(R.id.txtExpiringCount);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        pantryDAO = new PantryDAO(this);
        pantryItems = new ArrayList<>();

        pantryAdapter = new PantryAdapter(
                pantryItems,
                item -> {
                    Intent intent = new Intent(
                            MainActivity.this,
                            AddEditIngredientActivity.class
                    );

                    intent.putExtra("item_id", item.getId());
                    startActivity(intent);
                }
        );

        recyclerPantry.setAdapter(pantryAdapter);

        btnAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class
            );

            startActivity(intent);
        });

        bottomNavigation.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.nav_pantry) {

                return true;

            } else if (itemId == R.id.nav_recipes) {

                Intent intent = new Intent(
                        MainActivity.this,
                        SuggestedRecipesActivity.class
                );

                startActivity(intent);

                return true;

            } else if (itemId == R.id.nav_settings) {

                Intent intent = new Intent(
                        MainActivity.this,
                        SettingsActivity.class
                );

                startActivity(intent);

                return true;
            }

            return false;
        });

        bottomNavigation.setSelectedItemId(R.id.nav_pantry);

        loadPantryItems();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (pantryDAO != null) {
            loadPantryItems();
        }
    }

    private void loadPantryItems() {

        List<PantryItem> databaseItems = pantryDAO.getAllItems();

        pantryItems.clear();
        pantryItems.addAll(databaseItems);
        txtPantryCount.setText(String.valueOf(pantryItems.size()));
        txtExpiringCount.setText(
                String.valueOf(pantryDAO.getExpiringSoonCount())
        );

        pantryAdapter.notifyDataSetChanged();

        if (pantryItems.isEmpty()) {
            recyclerPantry.setVisibility(View.GONE);
            txtEmptyPantry.setVisibility(View.VISIBLE);
        } else {
            recyclerPantry.setVisibility(View.VISIBLE);
            txtEmptyPantry.setVisibility(View.GONE);
        }
    }
}