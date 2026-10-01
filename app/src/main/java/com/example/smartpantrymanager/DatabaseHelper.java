package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 2;

    // Pantry table
    public static final String TABLE_PANTRY = "pantry_items";
    public static final String COL_PANTRY_ID = "id";
    public static final String COL_PANTRY_NAME = "name";
    public static final String COL_PANTRY_QUANTITY = "quantity";
    public static final String COL_PANTRY_UNIT = "unit";
    public static final String COL_PANTRY_EXPIRY = "expiry_date";

    // Recipes table
    public static final String TABLE_RECIPES = "recipes";
    public static final String COL_RECIPE_ID = "id";
    public static final String COL_RECIPE_NAME = "name";
    public static final String COL_RECIPE_INSTRUCTIONS = "instructions";

    // Recipe ingredients table
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";
    public static final String COL_INGREDIENT_ID = "id";
    public static final String COL_INGREDIENT_RECIPE_ID = "recipe_id";
    public static final String COL_INGREDIENT_NAME = "ingredient_name";
    public static final String COL_INGREDIENT_QUANTITY = "required_quantity";
    public static final String COL_INGREDIENT_UNIT = "unit";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Pantry items
        db.execSQL("CREATE TABLE " + TABLE_PANTRY + " (" +
                COL_PANTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PANTRY_NAME + " TEXT NOT NULL, " +
                COL_PANTRY_QUANTITY + " REAL NOT NULL, " +
                COL_PANTRY_UNIT + " TEXT NOT NULL, " +
                COL_PANTRY_EXPIRY + " TEXT)");

        // Recipes
        db.execSQL("CREATE TABLE " + TABLE_RECIPES + " (" +
                COL_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RECIPE_NAME + " TEXT NOT NULL, " +
                COL_RECIPE_INSTRUCTIONS + " TEXT NOT NULL)");

        // Recipe ingredients
        db.execSQL("CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                COL_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_INGREDIENT_RECIPE_ID + " INTEGER NOT NULL, " +
                COL_INGREDIENT_NAME + " TEXT NOT NULL, " +
                COL_INGREDIENT_QUANTITY + " REAL NOT NULL, " +
                COL_INGREDIENT_UNIT + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + COL_INGREDIENT_RECIPE_ID + ") " +
                "REFERENCES " + TABLE_RECIPES + "(" + COL_RECIPE_ID + "))");

        seedRecipes(db);
    }

    private void seedRecipes(SQLiteDatabase db) {

        addRecipe(db, "Tomato Pasta",
                "Cook pasta. Prepare tomato sauce with tomatoes, onion and garlic. Combine and serve.",
                new String[][]{
                        {"pasta", "200", "g"},
                        {"tomato", "3", "pcs"},
                        {"onion", "1", "pcs"},
                        {"garlic", "2", "pcs"}
                });

        addRecipe(db, "Chicken Rice",
                "Cook rice. Cook chicken with onion and garlic. Combine and serve.",
                new String[][]{
                        {"rice", "200", "g"},
                        {"chicken", "200", "g"},
                        {"onion", "1", "pcs"},
                        {"garlic", "2", "pcs"}
                });

        addRecipe(db, "Vegetable Omelette",
                "Beat eggs. Add vegetables and cook in a pan until set.",
                new String[][]{
                        {"egg", "3", "pcs"},
                        {"tomato", "1", "pcs"},
                        {"onion", "1", "pcs"}
                });

        addRecipe(db, "Grilled Cheese Sandwich",
                "Place cheese between bread slices and grill until golden.",
                new String[][]{
                        {"bread", "2", "slices"},
                        {"cheese", "50", "g"}
                });

        addRecipe(db, "Chicken Sandwich",
                "Cook chicken and place it with lettuce and tomato between bread slices.",
                new String[][]{
                        {"bread", "2", "slices"},
                        {"chicken", "100", "g"},
                        {"lettuce", "50", "g"},
                        {"tomato", "1", "pcs"}
                });

        addRecipe(db, "Beef Stir Fry",
                "Stir fry beef with vegetables, garlic and soy sauce.",
                new String[][]{
                        {"beef", "200", "g"},
                        {"pepper", "1", "pcs"},
                        {"onion", "1", "pcs"},
                        {"garlic", "2", "pcs"},
                        {"soy sauce", "30", "ml"}
                });

        addRecipe(db, "Pancakes",
                "Mix flour, milk and eggs. Cook portions in a hot pan.",
                new String[][]{
                        {"flour", "200", "g"},
                        {"milk", "250", "ml"},
                        {"egg", "2", "pcs"}
                });

        addRecipe(db, "French Toast",
                "Dip bread in beaten egg and milk. Fry until golden.",
                new String[][]{
                        {"bread", "2", "slices"},
                        {"egg", "2", "pcs"},
                        {"milk", "100", "ml"}
                });

        addRecipe(db, "Chicken Curry",
                "Cook chicken with onion, tomato and curry powder. Simmer until cooked.",
                new String[][]{
                        {"chicken", "300", "g"},
                        {"onion", "1", "pcs"},
                        {"tomato", "2", "pcs"},
                        {"curry powder", "10", "g"}
                });

        addRecipe(db, "Tuna Pasta",
                "Cook pasta. Mix with tuna and tomato sauce.",
                new String[][]{
                        {"pasta", "200", "g"},
                        {"tuna", "150", "g"},
                        {"tomato", "2", "pcs"}
                });

        addRecipe(db, "Garden Salad",
                "Chop vegetables and combine in a bowl.",
                new String[][]{
                        {"lettuce", "100", "g"},
                        {"tomato", "2", "pcs"},
                        {"cucumber", "1", "pcs"},
                        {"onion", "1", "pcs"}
                });

        addRecipe(db, "Egg Fried Rice",
                "Stir fry cooked rice with eggs, onion and soy sauce.",
                new String[][]{
                        {"rice", "250", "g"},
                        {"egg", "2", "pcs"},
                        {"onion", "1", "pcs"},
                        {"soy sauce", "20", "ml"}
                });

        addRecipe(db, "Garlic Bread",
                "Mix garlic with butter. Spread on bread and bake until golden.",
                new String[][]{
                        {"bread", "4", "slices"},
                        {"garlic", "3", "pcs"},
                        {"butter", "30", "g"}
                });

        addRecipe(db, "Creamy Chicken Pasta",
                "Cook pasta. Cook chicken and combine with cream and pasta.",
                new String[][]{
                        {"pasta", "200", "g"},
                        {"chicken", "200", "g"},
                        {"cream", "100", "ml"},
                        {"garlic", "2", "pcs"}
                });

        addRecipe(db, "Beef Burger",
                "Cook the beef patty and serve in a bun with lettuce and tomato.",
                new String[][]{
                        {"beef", "150", "g"},
                        {"bread", "1", "pcs"},
                        {"lettuce", "30", "g"},
                        {"tomato", "1", "pcs"}
                });

        addRecipe(db, "Tomato Soup",
                "Cook tomatoes with onion and garlic. Blend until smooth.",
                new String[][]{
                        {"tomato", "4", "pcs"},
                        {"onion", "1", "pcs"},
                        {"garlic", "2", "pcs"}
                });

        addRecipe(db, "Chicken Wrap",
                "Cook chicken and wrap with lettuce, tomato and cheese.",
                new String[][]{
                        {"chicken", "150", "g"},
                        {"lettuce", "50", "g"},
                        {"tomato", "1", "pcs"},
                        {"cheese", "50", "g"}
                });

        addRecipe(db, "Vegetable Rice",
                "Cook rice and stir fry with mixed vegetables.",
                new String[][]{
                        {"rice", "200", "g"},
                        {"carrot", "1", "pcs"},
                        {"pepper", "1", "pcs"},
                        {"onion", "1", "pcs"}
                });

        addRecipe(db, "Cheese Omelette",
                "Beat eggs with cheese and cook in a pan.",
                new String[][]{
                        {"egg", "3", "pcs"},
                        {"cheese", "50", "g"}
                });

        addRecipe(db, "Chicken Salad",
                "Cook chicken and combine with lettuce, tomato and cucumber.",
                new String[][]{
                        {"chicken", "150", "g"},
                        {"lettuce", "100", "g"},
                        {"tomato", "1", "pcs"},
                        {"cucumber", "1", "pcs"}
                });
    }

    private void addRecipe(SQLiteDatabase db, String name,
                           String instructions, String[][] ingredients) {

        ContentValues recipeValues = new ContentValues();
        recipeValues.put(COL_RECIPE_NAME, name);
        recipeValues.put(COL_RECIPE_INSTRUCTIONS, instructions);

        long recipeId = db.insert(TABLE_RECIPES, null, recipeValues);

        for (String[] ingredient : ingredients) {
            ContentValues ingredientValues = new ContentValues();
            ingredientValues.put(COL_INGREDIENT_RECIPE_ID, recipeId);
            ingredientValues.put(COL_INGREDIENT_NAME, ingredient[0]);
            ingredientValues.put(COL_INGREDIENT_QUANTITY,
                    Double.parseDouble(ingredient[1]));
            ingredientValues.put(COL_INGREDIENT_UNIT, ingredient[2]);

            db.insert(TABLE_RECIPE_INGREDIENTS, null, ingredientValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        onCreate(db);
    }
}