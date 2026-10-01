# Smart Pantry Manager

## App Description

Smart Pantry Manager is a Java-based Android application developed using Android Studio. The app helps users manage ingredients stored in their pantry and find recipes that can be prepared using the ingredients currently available.

The application allows users to:

* Add pantry ingredients
* Edit pantry ingredients
* Delete pantry ingredients
* Store ingredient quantities and units
* Add optional expiry dates
* View pantry items
* View recipes that can currently be prepared
* View recipe ingredients and preparation instructions
* Manage expiry-alert settings

The application uses strict recipe matching. A recipe is suggested only when all of its required ingredients are available in the pantry in the required quantities.

## Technologies Used

* Java
* Android Studio
* Android SDK
* SQLite
* RecyclerView
* Android Intents
* Material Design components

## Database

The application uses **SQLite** for local data storage.

SQLite was chosen because it is built into Android, does not require an external server or internet connection, and is suitable for storing pantry items and recipe data locally on the device.

The database stores:

* Pantry ingredients
* Ingredient quantities
* Units
* Expiry dates
* Recipes
* Recipe ingredients
* Recipe preparation instructions

## Main Screens

The application contains the following screens:

1. Pantry List
2. Add/Edit Ingredient
3. Suggested Recipes
4. Recipe Detail
5. Settings

## Recipe Matching

The recipe matching system checks every ingredient required by a recipe against the ingredients stored in the pantry.

A recipe is suggested only when:

* Every required ingredient is present.
* The available quantity is equal to or greater than the required quantity.
* Simple ingredient name differences such as singular and plural forms are handled.
* Compatible units such as kilograms/grams and litres/millilitres can be converted.

Recipes with missing ingredients or insufficient quantities are not displayed as suggestions.

## Setup and Running the Application

1. Clone or download the repository.
2. Open the project in Android Studio.
3. Allow Android Studio to synchronise the Gradle project.
4. Connect an Android device or start an Android emulator.
5. Select the `app` configuration.
6. Click **Run** to build and launch the application.

## Requirements

* Android Studio
* Android SDK
* Java Development Kit (JDK)
* Android device or emulator

## Project Package

```text
com.example.smartpantrymanager
```

## Version

Version 1.0
