# Food Recipes
Welcome to the **Food Recipes** Android application!
This project, developed by [Sergey Tosunyan](https://www.linkedin.com/in/sergey-tosunyan-543882216/), serves as both as a personal portfolio and a reference for other developers. The goal is to showcase best practices in Android development using modern technologies and architectural patterns.

## Overview
**Food Recipes** is an Android app that provides users with various recipes from around the world. It fetches data from [TheMealDB API](https://www.themealdb.com/api.php), allowing users to explore recipes by categories, regions, and more.

<img width="180px" src="https://github.com/user-attachments/assets/1035c150-7d4d-45f7-b4bf-d235aa08af9c" alt="Home"/>
<img width="180px" src="https://github.com/user-attachments/assets/4da3a93d-d495-4320-85b0-032850836812" alt="Search"/>
<img width="180px" src="https://github.com/user-attachments/assets/0b5ba10f-68b0-449d-88d4-a7f258ae82a7" alt="Saved Meals"/>
<img width="180px" src="https://github.com/user-attachments/assets/74f62409-7c75-4319-a2d3-afdc46e7f52f" alt="Category"/>
<img width="180px" src="https://github.com/user-attachments/assets/d3c6558b-de5a-4655-801b-9207928ba8a3" alt="Meal Details"/>


## Features
* **Daily Special**: Discover a new featured recipe every day
* **Categories**: Browse recipes by various categories
* **Regions**: Explore recipes from different regions
* **Search**: Search recipes by their name
* **Saved Meals**: Save your favorite recipes for quick access later

## Tech Stack
* Kotlin Coroutines, Flows
* MVVM, Multi-module
* Jetpack Compose
* Custom design system
* Voyager
* Retrofit
* Ktor
* Room Database
* Koin
* Local properties
* GitHub Actions

## Getting Started
To set up this project on your local machine, follow these steps:
1. Clone the repository
2. Setup `local.properties` file in the root directory
    ```properties
    KEY_ALIAS=food-recipes
    KEY_PASSWORD=iM3hQqO1s1wUtDqr
    STORE_PASSWORD=iM3hQqO1s1wUtDqr

    MEAL_API_KEY="1"
    MEAL_API_URL="https://www.themealdb.com/api/json/v1"
    ```
3. Create `prod.properties` file in the root directory, with [required properties](https://docs.google.com/spreadsheets/d/1MuSYiBYV134sgj6BprrIJxMGM5QlVHRFwMjyO-bLQik/edit?usp=drive_link):
4. Sync and run the project
