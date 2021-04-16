package com.example.pizzaHut.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pizzaHut.R;
import com.example.pizzaHut.adapters.IngredientAdapterD;
import com.example.pizzaHut.database.MealDatabase;
import com.example.pizzaHut.databinding.FragmentDescriptionBinding;
import com.example.pizzaHut.models.IngredientD;
import com.example.pizzaHut.models.Meal;
import com.example.pizzaHut.responses.MealResponse;
import com.example.pizzaHut.viewmodels.DescriptionViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.example.pizzaHut.fragments.DescriptionFragmentArgs.fromBundle;

public class DescriptionFragment extends Fragment {

    private FragmentDescriptionBinding binding;
    private DescriptionViewModel viewModel;
    private String youtubeLink, sourceLink;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_description, container, false);
        viewModel = new ViewModelProvider(this).get(DescriptionViewModel.class);

        if (fromBundle(getArguments()).getMealName() == null)
            getMealInfoFromParcelable();
        else getMealInfoFromApi();

        binding.tvInstruction.setVisibility(View.VISIBLE);
        binding.ingredientList.setVisibility(View.VISIBLE);

        onClick();
        return binding.getRoot();
    }

    private void getMealInfoFromApi() {
        viewModel.getMealInfo(fromBundle(requireArguments()).getMealName()).observe(getViewLifecycleOwner(), mealResponse -> {
            if (mealResponse.getMeals() != null) {
                binding.setMeal(mealResponse.getMeals().get(0));

                youtubeLink = mealResponse.getMeals().get(0).getStrYoutube();
                sourceLink = mealResponse.getMeals().get(0).getStrSource();

                initRecyclerView(new IngredientAdapterD(initIngredients(mealResponse)));
            }
        });
    }

    private void getMealInfoFromParcelable() {
        binding.setMeal(fromBundle(getArguments()).getMealModel());

        sourceLink = fromBundle(getArguments()).getMealModel().getStrSource();
        youtubeLink = fromBundle(getArguments()).getMealModel().getStrYoutube();

        initRecyclerView(new IngredientAdapterD(initIngredients()));
    }

    private void initRecyclerView(IngredientAdapterD adapter) {
        binding.ingredientList.setAdapter(adapter);
        binding.ingredientList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.ingredientList.setHasFixedSize(true);
    }

    @NotNull
    private List<IngredientD> initIngredients() {
        List<IngredientD> ingredients = new ArrayList<>();
        Meal meal = fromBundle(getArguments()).getMealModel();
        ingredients.add(new IngredientD(meal.getStrIngredient1(), meal.getStrMeasure1()));
        ingredients.add(new IngredientD(meal.getStrIngredient2(), meal.getStrMeasure2()));
        ingredients.add(new IngredientD(meal.getStrIngredient3(), meal.getStrMeasure3()));
        ingredients.add(new IngredientD(meal.getStrIngredient4(), meal.getStrMeasure4()));
        ingredients.add(new IngredientD(meal.getStrIngredient5(), meal.getStrMeasure5()));
        ingredients.add(new IngredientD(meal.getStrIngredient6(), meal.getStrMeasure6()));
        ingredients.add(new IngredientD(meal.getStrIngredient7(), meal.getStrMeasure7()));
        ingredients.add(new IngredientD(meal.getStrIngredient8(), meal.getStrMeasure8()));
        ingredients.add(new IngredientD(meal.getStrIngredient9(), meal.getStrMeasure9()));
        ingredients.add(new IngredientD(meal.getStrIngredient10(), meal.getStrMeasure10()));
        ingredients.add(new IngredientD(meal.getStrIngredient11(), meal.getStrMeasure11()));
        ingredients.add(new IngredientD(meal.getStrIngredient12(), meal.getStrMeasure12()));
        ingredients.add(new IngredientD(meal.getStrIngredient13(), meal.getStrMeasure13()));
        ingredients.add(new IngredientD(meal.getStrIngredient14(), meal.getStrMeasure14()));
        ingredients.add(new IngredientD(meal.getStrIngredient15(), meal.getStrMeasure15()));
        ingredients.add(new IngredientD(meal.getStrIngredient16(), meal.getStrMeasure16()));
        ingredients.add(new IngredientD(meal.getStrIngredient17(), meal.getStrMeasure17()));
        ingredients.add(new IngredientD(meal.getStrIngredient18(), meal.getStrMeasure18()));
        ingredients.add(new IngredientD(meal.getStrIngredient19(), meal.getStrMeasure19()));
        ingredients.add(new IngredientD(meal.getStrIngredient20(), meal.getStrMeasure20()));
        return ingredients;
    }

    @NotNull
    private List<IngredientD> initIngredients(@NotNull MealResponse mealResponse) {
        List<IngredientD> ingredients = new ArrayList<>();
        if (mealResponse.getMeals() != null) {
            Meal meal = mealResponse.getMeals().get(0);
            ingredients.add(new IngredientD(meal.getStrIngredient1(), meal.getStrMeasure1()));
            ingredients.add(new IngredientD(meal.getStrIngredient2(), meal.getStrMeasure2()));
            ingredients.add(new IngredientD(meal.getStrIngredient3(), meal.getStrMeasure3()));
            ingredients.add(new IngredientD(meal.getStrIngredient4(), meal.getStrMeasure4()));
            ingredients.add(new IngredientD(meal.getStrIngredient5(), meal.getStrMeasure5()));
            ingredients.add(new IngredientD(meal.getStrIngredient6(), meal.getStrMeasure6()));
            ingredients.add(new IngredientD(meal.getStrIngredient7(), meal.getStrMeasure7()));
            ingredients.add(new IngredientD(meal.getStrIngredient8(), meal.getStrMeasure8()));
            ingredients.add(new IngredientD(meal.getStrIngredient9(), meal.getStrMeasure9()));
            ingredients.add(new IngredientD(meal.getStrIngredient10(), meal.getStrMeasure10()));
            ingredients.add(new IngredientD(meal.getStrIngredient11(), meal.getStrMeasure11()));
            ingredients.add(new IngredientD(meal.getStrIngredient12(), meal.getStrMeasure12()));
            ingredients.add(new IngredientD(meal.getStrIngredient13(), meal.getStrMeasure13()));
            ingredients.add(new IngredientD(meal.getStrIngredient14(), meal.getStrMeasure14()));
            ingredients.add(new IngredientD(meal.getStrIngredient15(), meal.getStrMeasure15()));
            ingredients.add(new IngredientD(meal.getStrIngredient16(), meal.getStrMeasure16()));
            ingredients.add(new IngredientD(meal.getStrIngredient17(), meal.getStrMeasure17()));
            ingredients.add(new IngredientD(meal.getStrIngredient18(), meal.getStrMeasure18()));
            ingredients.add(new IngredientD(meal.getStrIngredient19(), meal.getStrMeasure19()));
            ingredients.add(new IngredientD(meal.getStrIngredient20(), meal.getStrMeasure20()));
        }
        return ingredients;
    }

    private void onClick() {
        binding.btnBackToMeals.setOnClickListener(v -> DescriptionFragment.this.requireActivity().onBackPressed());
        binding.btnYoutubeLink.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))));

        binding.btnSourceLink.setOnClickListener(v -> {
            if (sourceLink != null)
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sourceLink)));
            else Toast.makeText(requireContext(), "Source link is NULL", Toast.LENGTH_SHORT).show();
        });

        binding.btnSaveMeal.setOnClickListener(v -> {
            MealResponse response = new MealResponse();

            if (response.getMeals() != null) {
                MealDatabase.getInstance(requireContext()).mealDao().insertMeal(new Meal(
                        response.getMeals().get(0).getStrMeal(),
                        response.getMeals().get(0).getStrCategory(),
                        response.getMeals().get(0).getStrArea(),
                        response.getMeals().get(0).getStrInstructions(),
                        response.getMeals().get(0).getStrMealThumb(),
                        response.getMeals().get(0).getStrYoutube(),
                        response.getMeals().get(0).getStrSource(),

                        response.getMeals().get(0).getStrIngredient1(),
                        response.getMeals().get(0).getStrIngredient2(),
                        response.getMeals().get(0).getStrIngredient3(),
                        response.getMeals().get(0).getStrIngredient4(),
                        response.getMeals().get(0).getStrIngredient5(),
                        response.getMeals().get(0).getStrIngredient6(),
                        response.getMeals().get(0).getStrIngredient7(),
                        response.getMeals().get(0).getStrIngredient8(),
                        response.getMeals().get(0).getStrIngredient9(),
                        response.getMeals().get(0).getStrIngredient10(),
                        response.getMeals().get(0).getStrIngredient11(),
                        response.getMeals().get(0).getStrIngredient12(),
                        response.getMeals().get(0).getStrIngredient13(),
                        response.getMeals().get(0).getStrIngredient14(),
                        response.getMeals().get(0).getStrIngredient15(),
                        response.getMeals().get(0).getStrIngredient16(),
                        response.getMeals().get(0).getStrIngredient17(),
                        response.getMeals().get(0).getStrIngredient18(),
                        response.getMeals().get(0).getStrIngredient19(),
                        response.getMeals().get(0).getStrIngredient20(),

                        response.getMeals().get(0).getStrMeasure1(),
                        response.getMeals().get(0).getStrMeasure2(),
                        response.getMeals().get(0).getStrMeasure3(),
                        response.getMeals().get(0).getStrMeasure4(),
                        response.getMeals().get(0).getStrMeasure5(),
                        response.getMeals().get(0).getStrMeasure6(),
                        response.getMeals().get(0).getStrMeasure7(),
                        response.getMeals().get(0).getStrMeasure8(),
                        response.getMeals().get(0).getStrMeasure9(),
                        response.getMeals().get(0).getStrMeasure10(),
                        response.getMeals().get(0).getStrMeasure11(),
                        response.getMeals().get(0).getStrMeasure12(),
                        response.getMeals().get(0).getStrMeasure13(),
                        response.getMeals().get(0).getStrMeasure14(),
                        response.getMeals().get(0).getStrMeasure15(),
                        response.getMeals().get(0).getStrMeasure16(),
                        response.getMeals().get(0).getStrMeasure17(),
                        response.getMeals().get(0).getStrMeasure18(),
                        response.getMeals().get(0).getStrMeasure19(),
                        response.getMeals().get(0).getStrMeasure20()
                ));
            }

            binding.btnSaveMeal.setImageResource(R.drawable.ic_star_plus);

            Toast.makeText(getContext(), "Added to Database", Toast.LENGTH_SHORT).show();
        });
    }
}