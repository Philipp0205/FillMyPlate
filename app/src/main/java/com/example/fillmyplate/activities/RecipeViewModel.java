package com.example.fillmyplate.activities;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fillmyplate.entitys.Ingredient;
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
        Log.d(TAG, "insertRecipe: " + recipe.toString());
        mRepository.insert(recipe);
    }

    public void insertRecipeWithIngredients (Recipe recipe) {
        mRepository.insertRecipeWithIngredients(recipe);
    }

    public void update (Recipe... recipes) {
        mRepository.update(recipes);
    }


    public LiveData<Recipe> getrecipeById(int id) {
        Log.d(TAG, "findById: " + id);

        return   mRepository.getRecipeById(id);
    }

    public LiveData<List<Ingredient>> getIngredientsWithRecipeId(int id) {
        return mRepository.getIngedientsWithRecipeId(id);
    }

    public LiveData<Recipe> getRecipeWithIngredients(int id) {
        return null;
    }

}
