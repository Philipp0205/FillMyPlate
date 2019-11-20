package com.example.fillmyplate.activities;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fillmyplate.entitys.Recipe;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private static final String TAG = "RecipeViewModel";

    private RecipeRepository mRepository;
    private LiveData<List<Recipe>> mAllRecipes;

    public RecipeViewModel(@NonNull Application application) {
        super(application);

        mRepository = new RecipeRepository(application);
        mAllRecipes = mRepository.getmAllRecipes();
    }

    LiveData<List<Recipe>> getAllRecipes() {return mAllRecipes;}

    public void insert (Recipe recipe) {
        Log.d(TAG, "insert: " + recipe.toString());
        mRepository.insert(recipe);
    }
}
