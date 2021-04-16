package com.example.pizzaHut.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pizzaHut.network.API;
import com.example.pizzaHut.network.RetrofitClient;
import com.example.pizzaHut.responses.CategoryResponse;
import com.example.pizzaHut.responses.IngredientResponse;
import com.example.pizzaHut.responses.MealResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentRepository {

    private final API api;
    private final MutableLiveData<MealResponse> mealResponseMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<CategoryResponse> categoryResponseMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<IngredientResponse> ingredientResponseMutableLiveData = new MutableLiveData<>();

    public HomeFragmentRepository() {
        api = RetrofitClient.getRetrofitInstance().create(API.class);
    }

    public LiveData<MealResponse> getRandomMeal() {
        api.getRandomMeal().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NotNull Call<MealResponse> call, @NotNull Response<MealResponse> response) {
                mealResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<MealResponse> call, @NotNull Throwable t) {
                mealResponseMutableLiveData.setValue(null);
            }
        });

        return mealResponseMutableLiveData;
    }

    public LiveData<CategoryResponse> getCategories() {
        api.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(@NotNull Call<CategoryResponse> call, @NotNull Response<CategoryResponse> response) {
                categoryResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<CategoryResponse> call, @NotNull Throwable t) {
                categoryResponseMutableLiveData.setValue(null);
            }
        });

        return categoryResponseMutableLiveData;
    }

    public LiveData<MealResponse> getAreas() {
        api.getAreas().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NotNull Call<MealResponse> call, @NotNull Response<MealResponse> response) {
                mealResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<MealResponse> call, @NotNull Throwable t) {
                mealResponseMutableLiveData.setValue(null);
            }
        });

        return mealResponseMutableLiveData;
    }

    public LiveData<IngredientResponse> getIngredients() {
        api.getIngredients().enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(@NotNull Call<IngredientResponse> call, @NotNull Response<IngredientResponse> response) {
                ingredientResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<IngredientResponse> call, @NotNull Throwable t) {
                ingredientResponseMutableLiveData.setValue(null);
            }
        });

        return ingredientResponseMutableLiveData;
    }
}