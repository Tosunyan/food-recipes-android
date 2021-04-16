package com.example.pizzaHut.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pizzaHut.network.API;
import com.example.pizzaHut.network.RetrofitClient;
import com.example.pizzaHut.responses.MealResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescriptionFragmentRepository {

    private final API api;
    private final MutableLiveData<MealResponse> data = new MutableLiveData<>();

    public DescriptionFragmentRepository() {
        api = RetrofitClient.getRetrofitInstance().create(API.class);
    }

    public LiveData<MealResponse> getMealDescription(String meal) {
        api.searchMealByName(meal).enqueue(new Callback<MealResponse>() {
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
                if (response.body() != null)
                    data.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<MealResponse> call, @NotNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<MealResponse> getMealsByFirstLetter(char letter) {
        api.searchMealByFirstLetter(letter).enqueue(new Callback<MealResponse>() {
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