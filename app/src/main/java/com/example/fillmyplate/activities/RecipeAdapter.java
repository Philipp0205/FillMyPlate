package com.example.fillmyplate.activities;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fillmyplate.R;
import com.example.fillmyplate.entitys.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private static final String TAG = "RecipeAdapter";

    private List<Recipe> mRecipes = new ArrayList<>();

    private LayoutInflater mInflater;


    private Context context;

    private static int backGroundIndex = 0;



    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView recipeTitleItemView;

        ImageView imageView;



        public RecipeViewHolder(View itemView) {
            super(itemView);

            recipeTitleItemView = itemView.findViewById(R.id.name);

            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.card_image_view);

            Log.d(TAG, "RecipeViewHolder: index " + backGroundIndex);

            if (backGroundIndex == 0) {
                imageView.setImageResource(R.drawable.background_green);
                backGroundIndex++;
            } else if (backGroundIndex == 1 ) {
                imageView.setImageResource(R.drawable.background_red);
                backGroundIndex++;
            } else if (backGroundIndex == 2 ) {
                imageView.setImageResource(R.drawable.background_blue);
                backGroundIndex = 0;
            }


        }

        @Override
        public void onClick(View v) {

            Log.d(TAG, "onClick: lol");


        }
    }

    public RecipeAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate an item view
        View mRecipeTitleView = mInflater.inflate(
                R.layout.recipe_list_row, parent, false);

        return new RecipeViewHolder(mRecipeTitleView);

    }

    // Get data into the corrsponding views
    @Override
    public void onBindViewHolder(RecipeAdapter.RecipeViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + position);
        Recipe currentRecipe = mRecipes.get(position);

        Log.d(TAG, "onBindViewHolder: setText " + currentRecipe);
        holder.recipeTitleItemView.setText(currentRecipe.getTitle());

    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public void setRecipes(List<Recipe> recipes) {
        this.mRecipes = recipes;
        Log.d(TAG, "setRecipes:  notifydataChanged" );
        notifyDataSetChanged();
    }


}
