package com.example.fillmyplate.activities;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.fillmyplate.db.IngredientsDao;
import com.example.fillmyplate.db.RecipeDao;
import com.example.fillmyplate.entitys.Ingredient;
import com.example.fillmyplate.entitys.Recipe;

import java.util.List;

public class RecipeRepository {

    private static final String TAG = "RecipeRepository";

    private RecipeDao mRecipeDao;
    private LiveData<List<Recipe>> mAllRecipes;

    private IngredientsDao mIngredientsDao;
    private LiveData<List<Ingredient>> mAllIngredients;

    RecipeRepository(Application application) {
        RecipeRoomDatabase db = RecipeRoomDatabase.getDatabase(application);
         mRecipeDao = db.recipeDao();
        mAllRecipes= mRecipeDao.getAllRecipes();
    }

    LiveData<List<Recipe>> getmAllRecipes() {
        return mAllRecipes;
    }

    public void insert (Recipe recipe) {
        Log.d(TAG, "insert: " + recipe.toString());
        new insertAsyncTask(mRecipeDao).execute(recipe);
    }

    private static class insertAsyncTask extends AsyncTask<Recipe, Void, Void> {

        private RecipeDao mAsyncTaskDao;

        insertAsyncTask(RecipeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Recipe... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
