package com.example.pizzaHut.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.pizzaHut.database.MealDao;
import com.example.pizzaHut.database.MealDatabase;
import com.example.pizzaHut.models.Meal;

import java.util.List;

public class MealRepository {

    private final MealDao mealDao;
    private final LiveData<List<Meal>> meals;

    public MealRepository(Application application) {
        MealDatabase database = MealDatabase.getInstance(application);
        mealDao = database.mealDao();
        meals = mealDao.getAllMeals();
    }

    public void insert(Meal meal) {
        mealDao.insertMeal(meal);
    }

    public void delete(Meal meal) {
        mealDao.deleteMeal(meal);
    }

    public LiveData<List<Meal>> getMeals() {
        return meals;
    }
}