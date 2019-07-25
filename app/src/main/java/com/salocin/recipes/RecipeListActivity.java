package com.salocin.recipes;

import android.view.View;
import android.os.Bundle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import com.salocin.recipes.adapters.OnRecipeListener;
import com.salocin.recipes.adapters.RecipeRecyclerAdapter;
import com.salocin.recipes.models.Recipe;
import com.salocin.recipes.util.Testing;
import com.salocin.recipes.viewmodels.RecipeListViewModel;

import java.util.List;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

    private RecipeListViewModel mRecipeListViewModel;
    private RecyclerView mRecyclerView;
    private RecipeRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);

        setContentView(R.layout.activity_recipe_list);

        subscribeObservers();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recipe_list);
        mAdapter = new RecipeRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void testRetrofitRequest() {
        mRecipeListViewModel.searchRecipesApi("chicken", 1);
    }

    public void subscribeObservers(){
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                mAdapter.setRecipes(recipes);
            }
        });
    }

    @Override
    public void onRecipeClick(int position) {
        //load detail page
    }

    @Override
    public void onCategoryClick(String category) {

    }
}
