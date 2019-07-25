package com.salocin.recipes.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.salocin.recipes.models.Recipe;

import java.util.List;

public class RecipeSearchResponse {
    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("recipes")
    @Expose
    private List<Recipe> recipes;

    public int getCount() {
        return count;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    @Override
    public String toString() {
        return "RecipeSearchResponse{" +
                "count=" + count +
                ", recipes=" + recipes +
                '}';
    }
}