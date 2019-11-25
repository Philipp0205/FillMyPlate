package com.example.fillmyplate.activities;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.fillmyplate.db.IngredientsDao;
import com.example.fillmyplate.entitys.Ingredient;
import com.example.fillmyplate.entitys.Ingredient;

import java.util.List;

public class IngredientsRepository {

    private static final String TAG = "IngredientRepository";

    private IngredientsDao mIngredientDao;
    private LiveData<List<Ingredient>> mAllIngredients;

    private IngredientsDao mIngredientsDao;

    IngredientsRepository(Application application) {
        RecipeRoomDatabase db = RecipeRoomDatabase.getDatabase(application);
         mIngredientDao = db.ingredientsDao();
         mAllIngredients= mIngredientDao.getAllIngredients();
    }

    LiveData<List<Ingredient>> getmAllIngredients() {
        return mAllIngredients;
    }

    public void insert (Ingredient ingredient) {
        Log.d(TAG, "insert: " + ingredient.toString());
        new insertAsyncTask(mIngredientDao).execute(ingredient);
    }

    private static class insertAsyncTask extends AsyncTask<Ingredient, Void, Void> {

        private IngredientsDao mAsyncTaskDao;

        insertAsyncTask(IngredientsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Ingredient... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
