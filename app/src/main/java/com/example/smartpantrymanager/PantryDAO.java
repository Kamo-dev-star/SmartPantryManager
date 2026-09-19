package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PantryDAO {

    private final DatabaseHelper dbHelper;

    public PantryDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // CREATE
    public long addItem(PantryItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_PANTRY_NAME, item.getName());
        values.put(DatabaseHelper.COL_PANTRY_QUANTITY, item.getQuantity());
        values.put(DatabaseHelper.COL_PANTRY_UNIT, item.getUnit());
        values.put(DatabaseHelper.COL_PANTRY_EXPIRY, item.getExpiryDate());

        long id = db.insert(DatabaseHelper.TABLE_PANTRY, null, values);
        db.close();

        return id;
    }

    // READ - get all pantry items
    public List<PantryItem> getAllItems() {
        List<PantryItem> items = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_PANTRY,
                null,
                null,
                null,
                null,
                null,
                DatabaseHelper.COL_PANTRY_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PANTRY_ID)
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PANTRY_NAME)
                );

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PANTRY_QUANTITY)
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PANTRY_UNIT)
                );

                String expiryDate = cursor.getString(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PANTRY_EXPIRY)
                );

                items.add(new PantryItem(
                        id,
                        name,
                        quantity,
                        unit,
                        expiryDate
                ));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return items;
    }

    // READ - get one pantry item
    public PantryItem getItemById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_PANTRY,
                null,
                DatabaseHelper.COL_PANTRY_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        PantryItem item = null;

        if (cursor.moveToFirst()) {
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PANTRY_NAME)
            );

            double quantity = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PANTRY_QUANTITY)
            );

            String unit = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PANTRY_UNIT)
            );

            String expiryDate = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PANTRY_EXPIRY)
            );

            item = new PantryItem(
                    id,
                    name,
                    quantity,
                    unit,
                    expiryDate
            );
        }

        cursor.close();
        db.close();

        return item;
    }

    // UPDATE
    public int updateItem(PantryItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_PANTRY_NAME, item.getName());
        values.put(DatabaseHelper.COL_PANTRY_QUANTITY, item.getQuantity());
        values.put(DatabaseHelper.COL_PANTRY_UNIT, item.getUnit());
        values.put(DatabaseHelper.COL_PANTRY_EXPIRY, item.getExpiryDate());

        int rowsUpdated = db.update(
                DatabaseHelper.TABLE_PANTRY,
                values,
                DatabaseHelper.COL_PANTRY_ID + "=?",
                new String[]{String.valueOf(item.getId())}
        );

        db.close();

        return rowsUpdated;
    }

    // DELETE
    public int deleteItem(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rowsDeleted = db.delete(
                DatabaseHelper.TABLE_PANTRY,
                DatabaseHelper.COL_PANTRY_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return rowsDeleted;
    }
}