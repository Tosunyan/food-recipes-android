package com.example.pizzaHut.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pizzaHut.repositories.HomeFragmentRepository;
import com.example.pizzaHut.responses.CategoryResponse;
import com.example.pizzaHut.responses.IngredientResponse;
import com.example.pizzaHut.responses.MealResponse;

public class HomeFragmentViewModel extends AndroidViewModel {

    private final HomeFragmentRepository repository;

    public HomeFragmentViewModel(Application application) {
        super(application);
        repository = new HomeFragmentRepository();
    }

    public LiveData<MealResponse> getRandomMeal() {
        return repository.getRandomMeal();
    }

    public LiveData<CategoryResponse> getCategories() {
        return repository.getCategories();
    }

    public LiveData<MealResponse> getAreas() {
        return repository.getAreas();
    }

    public LiveData<IngredientResponse> getIngredients() {
        return repository.getIngredients();
    }
}