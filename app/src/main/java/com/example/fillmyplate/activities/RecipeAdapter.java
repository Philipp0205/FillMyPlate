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

import java.util.List;
import java.util.Random;

class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private static final String TAG = "RecipeAdapter";

    private List<String> mTitleList;
    private LayoutInflater mInflater;

    private Context context;

    private static int backGroundIndex = 0;

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView recipeTitleItemView;
        final RecipeAdapter mAdapter;

        ImageView imageView;



        public RecipeViewHolder(@NonNull View itemView, RecipeAdapter adapter) {
            super(itemView);

            recipeTitleItemView = itemView.findViewById(R.id.name);
            this.mAdapter = adapter;


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

    public RecipeAdapter(Context context, List<String> titleList) {
        mInflater = LayoutInflater.from(context);
        this.mTitleList = titleList;

        this.context = context;
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate an item view
        View mRecipeTitleView = mInflater.inflate(
                R.layout.recipe_list_row, parent, false);

        return new RecipeViewHolder(mRecipeTitleView, this);

    }

    @Override
    public void onBindViewHolder(RecipeAdapter.RecipeViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: " + position);
        String mCurrentTitle = mTitleList.get(position);

        Log.d(TAG, "onBindViewHolder: setText " + mCurrentTitle);
        holder.recipeTitleItemView.setText(mCurrentTitle);

    }

    @Override
    public int getItemCount() {
        return mTitleList.size();
    }









}
