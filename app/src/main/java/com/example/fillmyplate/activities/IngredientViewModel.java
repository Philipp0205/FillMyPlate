package com.example.fillmyplate.activities;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fillmyplate.entitys.Ingredient;
import com.example.fillmyplate.entitys.Recipe;

import java.util.List;

public class IngredientViewModel extends AndroidViewModel {

    private static final String TAG = "IngredientViewModel";

    private IngredientsRepository mRepository;
    private LiveData<List<Ingredient>> mAllIngredients;

    public IngredientViewModel(@NonNull Application application) {
        super(application);

        mRepository = new IngredientsRepository(application);
        mAllIngredients = mRepository.getmAllIngredients();
    }

    LiveData<List<Ingredient>> getAllIngredients() {return mAllIngredients;}

    public void insert (Ingredient ingredient) {
        Log.d(TAG, "insert: " + ingredient.toString());
        mRepository.insert(ingredient);
    }

}
