package com.example.pizzaHut.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pizzaHut.network.API;
import com.example.pizzaHut.responses.MealResponse;
import com.example.pizzaHut.network.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealFragmentRepository {

    private final API api;
    private final MutableLiveData<MealResponse> data = new MutableLiveData<>();

    public MealFragmentRepository() {
        api = RetrofitClient.getRetrofitInstance().create(API.class);
    }

    public LiveData<MealResponse> filterMealsByCategory(String category) {
        api.filterMealsByCategory(category).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NotNull Call<MealResponse> call, @NotNull Response<MealResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<MealResponse> call, @NotNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<MealResponse> filterMealsByArea(String area) {
        api.filterMealsByArea(area).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NotNull Call<MealResponse> call, @NotNull Response<MealResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<MealResponse> call, @NotNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<MealResponse> filterMealsByIngredient(String ingredient) {
        api.filterMealsByIngredient(ingredient).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NotNull Call<MealResponse> call, @NotNull Response<MealResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<MealResponse> call, @NotNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}