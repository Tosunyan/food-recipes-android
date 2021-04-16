package com.example.pizzaHut.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.pizzaHut.adapters.MealAdapter;
import com.example.pizzaHut.adapters.MealAdapter.MealsItemClickListener;
import com.example.pizzaHut.databinding.FragmentSearchBinding;
import com.example.pizzaHut.models.Meal;
import com.example.pizzaHut.responses.MealResponse;
import com.example.pizzaHut.viewmodels.DescriptionViewModel;

import org.jetbrains.annotations.NotNull;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
import static androidx.navigation.Navigation.findNavController;
import static com.example.pizzaHut.fragments.SearchFragmentDirections.fromSearchToDescription;

public class SearchFragment extends Fragment implements MealsItemClickListener {

    private FragmentSearchBinding binding;
    private DescriptionViewModel viewModel;
    private int spanCount;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        spanCount = requireActivity().getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE ? ORIENTATION_LANDSCAPE : ORIENTATION_PORTRAIT;

        if (!binding.radioBtnMeal.isChecked() && !binding.radioBtnArea.isChecked() && !binding.radioBtnIngredients.isChecked())
            binding.radioBtnMeal.setChecked(true);

        viewModel = new ViewModelProvider(SearchFragment.this).get(DescriptionViewModel.class);
        getMeals();

        return binding.getRoot();
    }

    private void initRecyclerView(MealResponse mealResponse) {
        if (mealResponse != null) {
            MealAdapter mealAdapter = new MealAdapter(mealResponse.getMeals(), SearchFragment.this);
            binding.mealsList.setLayoutManager(new GridLayoutManager(getContext(), spanCount, GridLayoutManager.VERTICAL, false));
            binding.mealsList.setAdapter(mealAdapter);
            binding.mealsList.setHasFixedSize(true);
        }
    }

    private void getMeals() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.etSearch.getText() != null) {
                    String search = binding.etSearch.getText().toString();

                    if (binding.radioBtnMeal.isChecked())
                        if (binding.etSearch.getText().length() == 1)
                            viewModel.getMealsByFirstLetter(search.charAt(0)).observe(getViewLifecycleOwner(),
                                    mealResponse -> initRecyclerView(mealResponse));
                        else
                            viewModel.getMealInfo(search).observe(getViewLifecycleOwner(),
                                    mealResponse -> initRecyclerView(mealResponse));

                    else if (binding.radioBtnArea.isChecked())
                        viewModel.getMealsByArea(search).observe(getViewLifecycleOwner(),
                                mealResponse -> initRecyclerView(mealResponse));

                    else if (binding.radioBtnIngredients.isChecked())
                        viewModel.getMealsByIngredient(search).observe(getViewLifecycleOwner(),
                                mealResponse -> initRecyclerView(mealResponse));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onMealClick(@NotNull Meal meal) {
        findNavController(binding.getRoot()).navigate(fromSearchToDescription(meal.getStrMeal(), null));
    }

    @Override
    public void onMealLongClick(@NotNull String name) {
    }
}