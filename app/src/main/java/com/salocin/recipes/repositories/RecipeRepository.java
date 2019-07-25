package com.salocin.recipes.repositories;

import androidx.lifecycle.LiveData;
import com.salocin.recipes.models.Recipe;
import com.salocin.recipes.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeApiClient mRecipeApiClient;

    public static RecipeRepository getInstance(){
        if(instance == null){
            instance = new RecipeRepository();
        }
        return instance;
    }

    private RecipeRepository() {
        mRecipeApiClient = RecipeApiClient.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipeApiClient.getRecipes();
    }

    public void searchRecipesApi(String query, int page) {
        if(page == 0){
            page = 1;
        }
        mRecipeApiClient.searchRecipesApi(query, page);
    }
}
