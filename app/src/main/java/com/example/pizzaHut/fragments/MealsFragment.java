package com.example.pizzaHut.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaHut.R;
import com.example.pizzaHut.adapters.MealAdapter;
import com.example.pizzaHut.adapters.MealAdapter.MealsItemClickListener;
import com.example.pizzaHut.databinding.FragmentMealsBinding;
import com.example.pizzaHut.models.Meal;
import com.example.pizzaHut.responses.MealResponse;
import com.example.pizzaHut.viewmodels.DescriptionViewModel;
import com.example.pizzaHut.viewmodels.MealsFragmentViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static androidx.navigation.Navigation.findNavController;
import static com.example.pizzaHut.fragments.MealsFragmentArgs.fromBundle;
import static com.example.pizzaHut.fragments.MealsFragmentDirections.fromMealsToDescription;
import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE;

public class MealsFragment extends Fragment implements MealsItemClickListener {

    private FragmentMealsBinding binding;
    private MealsFragmentViewModel viewModel;
    private DescriptionViewModel descriptionViewModel;
    private MealAdapter adapter;
    private List<Meal> meals;
    private String title;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMealsBinding.inflate(inflater);
        title = fromBundle(requireArguments()).getTitle();
        viewModel = new ViewModelProvider(requireActivity()).get(MealsFragmentViewModel.class);
        descriptionViewModel = new ViewModelProvider(MealsFragment.this).get(DescriptionViewModel.class);
        int spanCount = getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE ? 2 : 1;

        switch (fromBundle(requireArguments()).getAction()) {
            case "Category": {
                binding.setDescription(fromBundle(requireArguments()).getDescription());
                viewModel.filterMealsByCategory(title).observe(requireActivity(), this::getMeals);
                break;
            }

            case "Area": {
                binding.btnShowDescription.setVisibility(View.GONE);
                viewModel.filterMealsByArea(title).observe(requireActivity(), this::getMeals);
                break;
            }

            case "Ingredients": {
                binding.setDescription(fromBundle(requireArguments()).getDescription());
                viewModel.filterMealsByIngredient(title).observe(requireActivity(), this::getMeals);
                break;
            }

            case "Database": {
                binding.btnShowDescription.setVisibility(View.GONE);
                viewModel.getMeals().observe(requireActivity(), this::getMeals);
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.mealsList);
                break;
            }
        }

        binding.setTitle(title);
        binding.mealsList.setAdapter(adapter);
        binding.mealsList.setLayoutManager(new GridLayoutManager(getContext(), spanCount));

        onClick();
        return binding.getRoot();
    }

    private final SimpleCallback simpleCallback = new SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getBindingAdapterPosition();

            viewModel.deleteMeal(meals.get(position));
            adapter.notifyItemRemoved(position);

            Snackbar
                    .make(binding.getRoot(), meals.get(position).getStrMeal() + " deleted", LENGTH_INDEFINITE)
                    .setTextColor(getResources().getColor(R.color.colorBackground, getActivity().getTheme()))
                    .setBackgroundTint(getResources().getColor(R.color.colorText, getActivity().getTheme()))
                    .setAction("Undo", v -> viewModel.insertMeal(meals.get(position)))
                    .setActionTextColor(getResources().getColor(R.color.colorAccent, getActivity().getTheme()))
                    .show();
        }
    };

    private void getMeals(List<Meal> meals) {
        this.meals = meals;
        adapter = new MealAdapter(meals, this);
    }

    private void getMeals(@NotNull MealResponse mealResponse) {
        adapter = new MealAdapter(mealResponse.getMeals(), MealsFragment.this);
    }

    @Override
    public void onMealClick(@NotNull Meal meal) {
        if (title.equals("Database"))
            findNavController(binding.getRoot()).navigate(fromMealsToDescription(null, meal));
        else
            findNavController(binding.getRoot()).navigate(fromMealsToDescription(meal.getStrMeal(), null));
    }

    @Override
    public void onMealLongClick(@NotNull String name) {
        if (!fromBundle(getArguments()).getAction().equals("Database")) {
            descriptionViewModel.getMealInfo(name).observe(MealsFragment.this, mealResponse ->
                    viewModel.insertMeal(new Meal(
                            mealResponse.getMeals().get(0).getStrMeal(),
                            mealResponse.getMeals().get(0).getStrCategory(),
                            mealResponse.getMeals().get(0).getStrArea(),
                            mealResponse.getMeals().get(0).getStrInstructions(),
                            mealResponse.getMeals().get(0).getStrMealThumb(),
                            mealResponse.getMeals().get(0).getStrYoutube(),
                            mealResponse.getMeals().get(0).getStrSource(),

                            mealResponse.getMeals().get(0).getStrIngredient1(),
                            mealResponse.getMeals().get(0).getStrIngredient2(),
                            mealResponse.getMeals().get(0).getStrIngredient3(),
                            mealResponse.getMeals().get(0).getStrIngredient4(),
                            mealResponse.getMeals().get(0).getStrIngredient5(),
                            mealResponse.getMeals().get(0).getStrIngredient6(),
                            mealResponse.getMeals().get(0).getStrIngredient7(),
                            mealResponse.getMeals().get(0).getStrIngredient8(),
                            mealResponse.getMeals().get(0).getStrIngredient9(),
                            mealResponse.getMeals().get(0).getStrIngredient10(),
                            mealResponse.getMeals().get(0).getStrIngredient11(),
                            mealResponse.getMeals().get(0).getStrIngredient12(),
                            mealResponse.getMeals().get(0).getStrIngredient13(),
                            mealResponse.getMeals().get(0).getStrIngredient14(),
                            mealResponse.getMeals().get(0).getStrIngredient15(),
                            mealResponse.getMeals().get(0).getStrIngredient16(),
                            mealResponse.getMeals().get(0).getStrIngredient17(),
                            mealResponse.getMeals().get(0).getStrIngredient18(),
                            mealResponse.getMeals().get(0).getStrIngredient19(),
                            mealResponse.getMeals().get(0).getStrIngredient20(),

                            mealResponse.getMeals().get(0).getStrMeasure1(),
                            mealResponse.getMeals().get(0).getStrMeasure2(),
                            mealResponse.getMeals().get(0).getStrMeasure3(),
                            mealResponse.getMeals().get(0).getStrMeasure4(),
                            mealResponse.getMeals().get(0).getStrMeasure5(),
                            mealResponse.getMeals().get(0).getStrMeasure6(),
                            mealResponse.getMeals().get(0).getStrMeasure7(),
                            mealResponse.getMeals().get(0).getStrMeasure8(),
                            mealResponse.getMeals().get(0).getStrMeasure9(),
                            mealResponse.getMeals().get(0).getStrMeasure10(),
                            mealResponse.getMeals().get(0).getStrMeasure11(),
                            mealResponse.getMeals().get(0).getStrMeasure12(),
                            mealResponse.getMeals().get(0).getStrMeasure13(),
                            mealResponse.getMeals().get(0).getStrMeasure14(),
                            mealResponse.getMeals().get(0).getStrMeasure15(),
                            mealResponse.getMeals().get(0).getStrMeasure16(),
                            mealResponse.getMeals().get(0).getStrMeasure17(),
                            mealResponse.getMeals().get(0).getStrMeasure18(),
                            mealResponse.getMeals().get(0).getStrMeasure19(),
                            mealResponse.getMeals().get(0).getStrMeasure20()
                    )));

            Toast.makeText(getContext(), "Added to Database", Toast.LENGTH_SHORT).show();
        }
    }

    private void onClick() {
        binding.btnBackToHome.setOnClickListener(v -> findNavController(binding.getRoot()).navigateUp());

        binding.btnShowDescription.setOnClickListener(v -> binding.tvDescription.setVisibility(
                binding.tvDescription.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
    }
}