package com.salocin.recipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.salocin.recipes.models.Recipe;
import com.salocin.recipes.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    private static RecipeRepository mRecipeRepository;

    public RecipeListViewModel() {
           mRecipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipeRepository.getRecipes();
    }

    public void searchRecipesApi(String query, int page) {
        mRecipeRepository.searchRecipesApi(query,page);
    }
}
