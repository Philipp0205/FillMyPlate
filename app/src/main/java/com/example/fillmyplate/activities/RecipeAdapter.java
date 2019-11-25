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
import androidx.lifecycle.ViewModel;
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


    private Context mContext;

    private static int backGroundIndex = 0;



    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView recipeTitleItemView;

        ImageView imageView;


        public RecipeViewHolder(View itemView) {
            super(itemView);

            recipeTitleItemView = itemView.findViewById(R.id.name);

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
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            //mRecipes.get(position).getUid();
            Log.d(TAG, "onClick: Position " + position);
            adapterListener.onClick(position);


        }
    }

    public RecipeAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.mRecipes = recipes;
        Log.d(TAG, "setRecipes:  notifydataChanged" );
        notifyDataSetChanged();
    }

    public interface AdapterListener {
        void onClick(int id);
        void onClick(ViewModel object);
    }

    private AdapterListener adapterListener;
    public void setAdapterListener(AdapterListener mCallback) {
        this.adapterListener = mCallback;

    }


}
