package com.example.pizzaHut.network;

import com.example.pizzaHut.responses.CategoryResponse;
import com.example.pizzaHut.responses.IngredientResponse;
import com.example.pizzaHut.responses.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("search.php")
    Call<MealResponse> searchMealByName(@Query("s") String name);

    @GET("search.php")
    Call<MealResponse> searchMealByFirstLetter(@Query("f") char firstLetter);


    @GET("random.php")
    Call<MealResponse> getRandomMeal();

    @GET("categories.php")
    Call<CategoryResponse> getCategories();

    @GET("list.php?a=list")
    Call<MealResponse> getAreas();

    @GET("list.php?i=list")
    Call<IngredientResponse> getIngredients();


    @GET("filter.php")
    Call<MealResponse> filterMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<MealResponse> filterMealsByArea(@Query("a") String area);

    @GET("filter.php")
    Call<MealResponse> filterMealsByIngredient(@Query("i") String ingredient);
}