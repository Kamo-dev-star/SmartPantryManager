package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPantry;
    private TextView txtEmptyPantry;
    private Button btnAddIngredient;

    private PantryDAO pantryDAO;
    private PantryAdapter pantryAdapter;
    private List<PantryItem> pantryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerPantry = findViewById(R.id.recyclerPantry);
        txtEmptyPantry = findViewById(R.id.txtEmptyPantry);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);

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