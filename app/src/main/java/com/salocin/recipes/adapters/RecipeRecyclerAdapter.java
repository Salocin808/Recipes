package com.salocin.recipes.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.salocin.recipes.R;
import com.salocin.recipes.models.Recipe;
import com.salocin.recipes.util.Constants;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int CATEGORY_TYPE = 3;

    private List<Recipe> mRecipes;
    private OnRecipeListener mOnRecipeListener;

    public RecipeRecyclerAdapter(OnRecipeListener mOnRecipeListener) {
        this.mOnRecipeListener = mOnRecipeListener;
        mRecipes = new ArrayList<>();
    }

    public void setRecipes(List<Recipe> mRecipes) {
        this.mRecipes = mRecipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        switch (viewType) {
            case RECIPE_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
                return new RecipeViewHolder(view, mOnRecipeListener);
            }
            case LOADING_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_list_item, parent, false);
                return new LoadingViewHolder(view);
            }
            case CATEGORY_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_list_item, parent, false);
                return new CategoryViewHolder(view, mOnRecipeListener);
            }

            default: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
                return new RecipeViewHolder(view, mOnRecipeListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == RECIPE_TYPE) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background);

            Glide.with(((RecipeViewHolder) holder).itemView)
                    .setDefaultRequestOptions(options)
                    .load(mRecipes.get(position).getImage_url())
                    .into(((RecipeViewHolder) holder).image);

            ((RecipeViewHolder) holder).title.setText(mRecipes.get(position).getTitle());
            ((RecipeViewHolder) holder).publisher.setText(mRecipes.get(position).getPublisher());
            ((RecipeViewHolder) holder).socialScore.setText(valueOf(Math.round(mRecipes.get(position).getSocial_rank())));
        }

        if (itemViewType == CATEGORY_TYPE) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background);

            Uri path = Uri.parse("android.resource://com.salocin.recipes/drawable/" + mRecipes.get(position).getImage_url());

            Glide.with(((CategoryViewHolder) holder).itemView)
                    .setDefaultRequestOptions(options)
                    .load(path)
                    .into(((CategoryViewHolder) holder).categoryImage);

            ((CategoryViewHolder) holder).categoryTitle.setText(mRecipes.get(position).getTitle());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mRecipes.get(position).getTitle().equals("LOADING..."))
            return LOADING_TYPE;
        else if(mRecipes.get(position).getSocial_rank() == -1)
            return CATEGORY_TYPE;
        else if(position == mRecipes.size() - 1
                && position != 0
                && !mRecipes.get(position).getTitle().equals("EXHAUSTED..."))
            return LOADING_TYPE;
        return RECIPE_TYPE;
    }

    public void displayLoading(){
        if(mRecipes != null){
            if(!isLoading()){
                Recipe recipe = new Recipe();
                recipe.setTitle("LOADING...");
                List<Recipe> loadingList = new ArrayList<>();
                loadingList.add(recipe);
                mRecipes = loadingList;
                notifyDataSetChanged();
            }
        }

    }

    public void displayCategories(){
        List<Recipe> categories = new ArrayList<>();
        for(int i = 0; i < Constants.DEFAULT_SEARCH_CATEGORIES.length; i++){
            Recipe recipe = new Recipe();
            recipe.setTitle(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
            recipe.setImage_url(Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]);
            recipe.setSocial_rank(-1);
            categories.add(recipe);
        }
        mRecipes = categories;
        notifyDataSetChanged();
    }

    private boolean isLoading(){
        if(mRecipes.size() > 0){
            if(mRecipes.get(mRecipes.size() - 1).getTitle().equals("LOADING..."))
                return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public Recipe getSelectedRecipe(int postion){
        if(mRecipes != null){
            if(mRecipes.size() > 0){
                return mRecipes.get(postion);
            }
        }
        return null;
    }
}
