package com.example.pizzaHut.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pizzaHut.models.Meal;
import com.example.pizzaHut.repositories.MealRepository;
import com.example.pizzaHut.responses.MealResponse;
import com.example.pizzaHut.repositories.MealFragmentRepository;

import java.util.List;

public class MealsFragmentViewModel extends AndroidViewModel {

    private final MealFragmentRepository mealFragmentRepository;
    private final MealRepository repository;
    private final LiveData<List<Meal>> meals;

    public MealsFragmentViewModel(Application application) {
        super(application);
        mealFragmentRepository = new MealFragmentRepository();
        repository = new MealRepository(application);
        meals = repository.getMeals();
    }

    public LiveData<MealResponse> filterMealsByCategory(String category) {
        return mealFragmentRepository.filterMealsByCategory(category);
    }

    public LiveData<MealResponse> filterMealsByArea(String area) {
        return mealFragmentRepository.filterMealsByArea(area);
    }

    public LiveData<MealResponse> filterMealsByIngredient(String ingredient) {
        return mealFragmentRepository.filterMealsByIngredient(ingredient);
    }

    public void insertMeal(Meal meal) {
        repository.insert(meal);
    }

    public void deleteMeal(Meal meal) {
        repository.delete(meal);
    }

    public LiveData<List<Meal>> getMeals() {
        return meals;
    }
}