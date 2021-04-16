package com.example.pizzaHut.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pizzaHut.models.Meal;

@Database(entities = {Meal.class}, version = 1, exportSchema = false)
public abstract class MealDatabase extends RoomDatabase {

    private static MealDatabase database;

    public static MealDatabase getInstance(Context context) {
        if (database == null)
            database = Room.databaseBuilder(context, MealDatabase.class, "MealDatabase")
                    .allowMainThreadQueries().build();

        return database;
    }

    public abstract MealDao mealDao();
}