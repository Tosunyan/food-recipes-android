package com.example.pizzaHut.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.pizzaHut.repositories.DescriptionFragmentRepository;
import com.example.pizzaHut.responses.MealResponse;

public class DescriptionViewModel extends ViewModel {

    private final DescriptionFragmentRepository repository;

    public DescriptionViewModel() {
        repository = new DescriptionFragmentRepository();
    }

    public LiveData<MealResponse> getMealInfo(String meal) {
        return repository.getMealDescription(meal);
    }

    public LiveData<MealResponse> getMealsByArea(String area) {
        return repository.filterMealsByArea(area);
    }

    public LiveData<MealResponse> getMealsByIngredient(String ingredient) {
        return repository.filterMealsByIngredient(ingredient);
    }

    public LiveData<MealResponse> getMealsByFirstLetter(char letter) {
        return repository.getMealsByFirstLetter(letter);
    }
}