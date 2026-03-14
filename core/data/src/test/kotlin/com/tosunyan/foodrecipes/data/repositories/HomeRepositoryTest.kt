package com.tosunyan.foodrecipes.data.repositories

import com.tosunyan.foodrecipes.common.coroutines.TestDispatcherProvider
import com.tosunyan.foodrecipes.data.mappers.toCategoryModel
import com.tosunyan.foodrecipes.data.mappers.toMealDetailsModel
import com.tosunyan.foodrecipes.data.mappers.toModel
import com.tosunyan.foodrecipes.data.mappers.toRegionModels
import com.tosunyan.foodrecipes.database.MealDatabase
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.network.api.ApiService
import com.tosunyan.foodrecipes.network.data.CategoryDto
import com.tosunyan.foodrecipes.network.data.ListDto
import com.tosunyan.foodrecipes.network.data.MealDetailsDto
import com.tosunyan.foodrecipes.network.data.RegionDto
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class HomeRepositoryTest {

    @RelaxedMockK private lateinit var apiService: ApiService
    @RelaxedMockK private lateinit var database: MealDatabase
    @RelaxedMockK private lateinit var randomMeal: MealDetailsDto

    private val categories = listOf(
        CategoryDto("Salad", "", ""),
        CategoryDto("Breakfast", "", ""),
        CategoryDto("Fancy", "", "")
    )

    private val regions = listOf(
        RegionDto("British"),
        RegionDto("Canadian"),
        RegionDto("American")
    )

    private val dispatcherProvider = TestDispatcherProvider
    private lateinit var repository: HomeRepository

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        mockkStatic(
            "com.tosunyan.foodrecipes.data.mappers.MealDetailsMapperKt",
            "com.tosunyan.foodrecipes.data.mappers.CategoryMapperKt",
            "com.tosunyan.foodrecipes.data.mappers.RegionMapperKt",
        )

        coEvery { database.mealDao.checkMealExists(any()) } returns false

        initRepository()
    }

    @Test
    fun `getRandomMeal returns null when exception occurs`() {
        runTest(dispatcherProvider.Main) {
            coEvery { apiService.getRandomMeal() } throws Exception("Network error")

            val result = repository.getRandomMeal()

            assertNull(result)
            coVerify(exactly = 1) { apiService.getRandomMeal() }
            verify(exactly = 0) { any<MealDetailsDto>().toMealDetailsModel() }
        }
    }

    @Test
    fun `getRandomMeal returns null when API returns empty list`() {
        runTest(dispatcherProvider.Main) {
            coEvery { apiService.getRandomMeal() } returns ListDto(items = emptyList())

            val result = repository.getRandomMeal()

            assertNull(result)
            coVerify(exactly = 1) { apiService.getRandomMeal() }
            verify(exactly = 0) { any<MealDetailsDto>().toMealDetailsModel() }
        }
    }

    @Test
    fun `getRandomMeal returns API result with correct saved status`() {
        runTest(dispatcherProvider.Main) {
            coEvery { apiService.getRandomMeal() } returns ListDto(items = listOf(randomMeal))
            coEvery { database.mealDao.getMealIds() } returns listOf("mealId")
            every { randomMeal.toMealDetailsModel(listOf("mealId")) } returns
                MealDetailsModel(id = "mealId", isSaved = true)

            val result = repository.getRandomMeal()

            assertEquals("mealId", result?.id)
            assertEquals(true, result?.isSaved)
            coVerify(exactly = 1) { apiService.getRandomMeal() }
            coVerify(exactly = 1) { database.mealDao.getMealIds() }
            verify(exactly = 1) { randomMeal.toMealDetailsModel(listOf("mealId")) }
        }
    }

    @Test
    fun `getRandomMeal returns cached result on subsequent calls`() {
        runTest(dispatcherProvider.Main) {
            coEvery { apiService.getRandomMeal() } returns ListDto(items = listOf(randomMeal))

            val mealDetails = MealDetailsModel()
            every { randomMeal.toMealDetailsModel(any()) } returns mealDetails

            val firstCallResult = repository.getRandomMeal()
            val secondCallResult = repository.getRandomMeal()

            assertEquals(mealDetails, firstCallResult)
            assertEquals(mealDetails, secondCallResult)
            coVerify(exactly = 1) { apiService.getRandomMeal() }
            verify(exactly = 1) { any<MealDetailsDto>().toMealDetailsModel() }
        }
    }

    @Test
    fun `getCategories returns empty list when exception occurs`() {
        runTest(dispatcherProvider.Main) {
            coEvery { apiService.getCategories() } throws Exception("Network error")

            val result = repository.getCategories()

            assertEquals(emptyList(), result)
            coVerify(exactly = 1) { apiService.getCategories() }
            verify(exactly = 0) { any<CategoryDto>().toCategoryModel() }
        }
    }

    @Test
    fun `getCategories returns empty list when API returns empty list`() {
        runTest(dispatcherProvider.Main) {
            coEvery { apiService.getCategories() } returns ListDto(items = emptyList())

            val result = repository.getCategories()

            assertEquals(emptyList(), result)
            coVerify(exactly = 1) { apiService.getCategories() }
            verify(exactly = 0) { any<CategoryDto>().toCategoryModel() }
        }
    }

    @Test
    fun `getCategories returns API result sorted by name`() {
        runTest(dispatcherProvider.Main) {
            coEvery { apiService.getCategories() } returns ListDto(items = categories)

            val result = repository.getCategories()

            assertEquals(3, result.size)
            assertEquals("Breakfast", result[0].name)
            assertEquals("Fancy", result[1].name)
            assertEquals("Salad", result[2].name)
            coVerify(exactly = 1) { apiService.getCategories() }
            verify(exactly = 3) { any<CategoryDto>().toCategoryModel() }
        }
    }

    @Test
    fun `getCategories returns cached result on subsequent calls`() {
        runTest(dispatcherProvider.Main) {
            coEvery { apiService.getCategories() } returns ListDto(items = categories)

            val firstCall = repository.getCategories()
            val secondCall = repository.getCategories()

            assertEquals(firstCall, secondCall)
            coVerify(exactly = 1) { apiService.getCategories() }
            verify(exactly = 3) { any<CategoryDto>().toCategoryModel() }
        }
    }

    @Test
    fun `getRegions returns empty list when exception occurs`() {
        runTest(dispatcherProvider.Main) {
            coEvery { apiService.getAreas() } throws Exception("Network error")

            val result = repository.getRegions()

            assertEquals(emptyList(), result)
            coVerify(exactly = 1) { apiService.getAreas() }
            verify(exactly = 0) { any<List<RegionDto>>().toRegionModels() }
        }
    }

    @Test
    fun `getRegions returns empty list when API returns empty list`() {
        runTest(dispatcherProvider.Main) {
            coEvery { apiService.getAreas() } returns ListDto(items = emptyList())

            val result = repository.getRegions()

            assertEquals(emptyList(), result)
            coVerify(exactly = 1) { apiService.getAreas() }
            verify(exactly = 1) { any<List<RegionDto>>().toRegionModels() }
        }
    }

    @Test
    fun `getRegions returns API result`() {
        runTest(dispatcherProvider.Main) {
            coEvery { apiService.getAreas() } returns ListDto(items = regions)

            val result = repository.getRegions()

            assertEquals(3, result.size)
            assertEquals("British", result[0].name)
            assertEquals("Canadian", result[1].name)
            assertEquals("American", result[2].name)
            coVerify(exactly = 1) { apiService.getAreas() }
            verify(exactly = 1) { any<List<RegionDto>>().toRegionModels() }
            verify(exactly = 3) { any<RegionDto>().toModel() }
        }
    }

    @Test
    fun `getRegions returns cached result on subsequent calls`() {
        runTest(dispatcherProvider.Main) {
            coEvery { apiService.getAreas() } returns ListDto(items = regions)

            val firstCall = repository.getRegions()
            val secondCall = repository.getRegions()

            assertEquals(firstCall, secondCall)
            coVerify(exactly = 1) { apiService.getAreas() }
            verify(exactly = 1) { any<List<RegionDto>>().toRegionModels() }
            verify(exactly = 3) { any<RegionDto>().toModel() }
        }
    }

    @AfterTest
    fun teardown() {
        clearAllMocks()
    }

    private fun initRepository() {
        repository = HomeRepository(
            dispatcherProvider = dispatcherProvider,
            apiService = apiService,
            database = database,
        )
    }
}