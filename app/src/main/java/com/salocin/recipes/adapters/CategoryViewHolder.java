package com.salocin.recipes.adapters;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.salocin.recipes.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "CategoryViewHolder";
    
    CircleImageView categoryImage;
    TextView categoryTitle;
    OnRecipeListener onRecipeListener;

    public CategoryViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
        super(itemView);
        categoryImage = itemView.findViewById(R.id.category_image);
        categoryTitle = itemView.findViewById(R.id.category_title);
        this.onRecipeListener = onRecipeListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: category was clicked!");
        onRecipeListener.onCategoryClick(categoryTitle.getText().toString());
    }
}
