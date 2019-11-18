package com.example.fillmyplate.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fillmyplate.entitys.Recipe;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private RecipeRepository mRepository;
    private LiveData<List<Recipe>> mAllRecipes;

    public RecipeViewModel(@NonNull Application application) {
        super(application);

        mRepository = new RecipeRepository(application);
        mAllRecipes = mRepository.getmAllRecipes();
    }

    LiveData<List<Recipe>> getmAllRecipes() {return mAllRecipes;}

    public void insert (Recipe recipe) {mRepository.insert(recipe);}
}
