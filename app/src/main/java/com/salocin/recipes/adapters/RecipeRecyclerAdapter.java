package com.salocin.recipes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.salocin.recipes.R;
import com.salocin.recipes.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
        return new RecipeViewHolder(view, mOnRecipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //view
        TextView title, publisher, socialScore;
        AppCompatImageView image;
        OnRecipeListener onRecipeListener;

        public RecipeViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
            super(itemView);

            this.onRecipeListener = onRecipeListener;

            title = itemView.findViewById(R.id.recipe_title);
            publisher = itemView.findViewById(R.id.recipe_publisher);
            socialScore = itemView.findViewById(R.id.recipe_social_score);
            image = itemView.findViewById(R.id.recipe_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecipeListener.onRecipeClick(getAdapterPosition());
        }
    }
}
