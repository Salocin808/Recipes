package com.salocin.recipes.requests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.salocin.recipes.models.Recipe;
import com.salocin.recipes.requests.responses.RecipeResponse;
import com.salocin.recipes.requests.responses.RecipeSearchResponse;
import com.salocin.recipes.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class RecipeApiClient {

    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> mRecipes;
    private MutableLiveData<Recipe> mRecipe;
    private Call<RecipeSearchResponse> callRecipes;
    private Call<RecipeResponse> callRecipe;

    public static RecipeApiClient getInstance() {
        if (instance == null)
            instance = new RecipeApiClient();
        return instance;
    }

    public RecipeApiClient() {
        mRecipes = new MutableLiveData<>();
        mRecipe = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    public LiveData<Recipe> getRecipe() {
        return mRecipe;
    }

    public void searchRecipesApi(String query, int page) {

        final int pageNumber = page;

        // make request
        callRecipes = ServiceGenerator.getRecipeApi().searchRecipe(
                Constants.API_KEY,
                query,
                String.valueOf(page));

        // get response and check response code
        callRecipes.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                if (response.code() == 200) {
                    // get response object from body
                    RecipeSearchResponse recipeSearchResponse = (RecipeSearchResponse) response.body();
                    List<Recipe> list = new ArrayList<>(recipeSearchResponse.getRecipes());

                    if (pageNumber == 1) {
                        mRecipes.postValue(list);
                    } else {
                        List<Recipe> recipes = mRecipes.getValue();
                        recipes.addAll(list);
                        mRecipes.postValue(recipes);
                    }
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                mRecipes.postValue(null);
                t.printStackTrace();
            }
        });
    }

    public void searchRecipeById(String rId){
        callRecipe = ServiceGenerator.getRecipeApi().getRecipe(Constants.API_KEY,rId);

        // get response and check response code
        callRecipe.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if (response.code() == 200) {
                    // get response object from body
                    RecipeResponse recipeResponse = (RecipeResponse) response.body();
                    Recipe recipe = recipeResponse.getRecipe();
                    mRecipe.postValue(recipe);
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                mRecipe.postValue(null);
                t.printStackTrace();
            }
        });

    }

    public void cancelRequest(){
        if(callRecipes != null)
            callRecipes.cancel();
    }
}
