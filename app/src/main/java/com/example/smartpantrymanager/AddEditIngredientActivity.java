package com.example.smartpantrymanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtQuantity;
    private EditText edtUnit;
    private EditText edtExpiry;

    private Button btnSave;
    private Button btnDelete;
    private Button btnSelectExpiry;

    private PantryDAO pantryDAO;

    private int itemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_edit_ingredient);

        edtName = findViewById(R.id.edtName);
        edtQuantity = findViewById(R.id.edtQuantity);
        edtUnit = findViewById(R.id.edtUnit);
        edtExpiry = findViewById(R.id.edtExpiry);

        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnSelectExpiry = findViewById(R.id.btnSelectExpiry);

        pantryDAO = new PantryDAO(this);

        itemId = getIntent().getIntExtra("item_id", -1);

        if (itemId != -1) {
            loadExistingItem();
            btnDelete.setVisibility(Button.VISIBLE);

            TextView txtFormTitle = findViewById(R.id.txtFormTitle);
            txtFormTitle.setText("Edit Ingredient");

        } else {
            btnDelete.setVisibility(Button.GONE);
        }

        btnSelectExpiry.setOnClickListener(v -> showDatePicker());

        btnSave.setOnClickListener(v -> saveItem());

        btnDelete.setOnClickListener(v -> deleteItem());
    }

    private void loadExistingItem() {

        PantryItem item = pantryDAO.getItemById(itemId);

        if (item != null) {
            edtName.setText(item.getName());
            edtQuantity.setText(String.valueOf(item.getQuantity()));
            edtUnit.setText(item.getUnit());
            edtExpiry.setText(item.getExpiryDate());
        }
    }

    private void saveItem() {

        String name = edtName.getText().toString().trim();
        String quantityText = edtQuantity.getText().toString().trim();
        String unit = edtUnit.getText().toString().trim();
        String expiry = edtExpiry.getText().toString().trim();

        if (name.isEmpty()) {
            edtName.setError("Ingredient name is required");
            edtName.requestFocus();
            return;
        }

        if (quantityText.isEmpty()) {
            edtQuantity.setError("Quantity is required");
            edtQuantity.requestFocus();
            return;
        }

        if (unit.isEmpty()) {
            edtUnit.setError("Unit is required");
            edtUnit.requestFocus();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            edtQuantity.setError("Enter a valid number");
            edtQuantity.requestFocus();
            return;
        }

        if (quantity <= 0) {
            edtQuantity.setError("Quantity must be greater than 0");
            edtQuantity.requestFocus();
            return;
        }

        PantryItem item = new PantryItem(
                itemId,
                name,
                quantity,
                unit,
                expiry
        );

        if (itemId == -1) {

            long result = pantryDAO.addItem(item);

            if (result != -1) {
                Toast.makeText(
                        this,
                        "Ingredient added successfully",
                        Toast.LENGTH_SHORT
                ).show();

                finish();
            } else {
                Toast.makeText(
                        this,
                        "Could not add ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }

        } else {

            int result = pantryDAO.updateItem(item);

            if (result > 0) {
                Toast.makeText(
                        this,
                        "Ingredient updated successfully",
                        Toast.LENGTH_SHORT
                ).show();

                finish();
            } else {
                Toast.makeText(
                        this,
                        "Could not update ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    private void deleteItem() {

        if (itemId != -1) {

            int result = pantryDAO.deleteItem(itemId);

            if (result > 0) {
                Toast.makeText(
                        this,
                        "Ingredient deleted successfully",
                        Toast.LENGTH_SHORT
                ).show();

                finish();
            } else {
                Toast.makeText(
                        this,
                        "Could not delete ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    private void showDatePicker() {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                android.R.style.Theme_Material_Dialog_Alert,
                (view, year, month, dayOfMonth) -> {

                    String date = String.format(
                            "%04d-%02d-%02d",
                            year,
                            month + 1,
                            dayOfMonth
                    );

                    edtExpiry.setText(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }
}