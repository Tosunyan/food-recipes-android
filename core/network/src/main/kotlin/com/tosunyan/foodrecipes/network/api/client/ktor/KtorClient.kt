package com.tosunyan.foodrecipes.network.api.client.ktor

import com.tosunyan.foodrecipes.network.api.ApiService
import com.tosunyan.foodrecipes.network.data.CategoryDto
import com.tosunyan.foodrecipes.network.data.ListDto
import com.tosunyan.foodrecipes.network.data.MealDetailsDto
import com.tosunyan.foodrecipes.network.data.MealDto
import com.tosunyan.foodrecipes.network.data.RegionDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class KtorClient(
    private val client: HttpClient,
) : ApiService {

    override suspend fun getRandomMeal(): ListDto<MealDetailsDto> {
        return client.get("random.php").body()
    }

    override suspend fun getCategories(): ListDto<CategoryDto> {
        return client.get("categories.php").body()
    }

    override suspend fun getAreas(): ListDto<RegionDto> {
        return client.get("list.php") {
            parameter("a", "list")
        }.body()
    }

    override suspend fun getMealDetails(id: String): ListDto<MealDetailsDto> {
        return client.get("lookup.php") {
            parameter("i", id)
        }.body()
    }

    override suspend fun searchMealByName(name: String): ListDto<MealDetailsDto> {
        return client.get("search.php") {
            parameter("s", name)
        }.body()
    }

    override suspend fun searchMealByFirstLetter(firstLetter: Char): ListDto<MealDetailsDto> {
        return client.get("search.php") {
            parameter("f", firstLetter)
        }.body()
    }

    override suspend fun filterMealsByCategory(category: String): ListDto<MealDto> {
        return client.get("filter.php") {
            parameter("c", category)
        }.body()
    }

    override suspend fun filterMealsByArea(area: String): ListDto<MealDto> {
        return client.get("filter.php") {
            parameter("a", area)
        }.body()
    }
}