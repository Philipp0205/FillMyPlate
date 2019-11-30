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

public class    RecipeRepository {

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

    LiveData<Recipe> getRecipeById(int id) {
        return mRecipeDao.getRecipeById(id);
    }

    LiveData<List<Ingredient>> getIngedientsWithRecipeId(int id) {return mRecipeDao.getIngredientsWithRecipeId(id);}

    LiveData<Recipe> findRecipeById(int id) {
        Log.d(TAG, "findRecipeById: " + id);

        return mRecipeDao.getRecipeById(id);
    }

    public void insert (Recipe recipe) {
        new insertAsyncTask(mRecipeDao).execute(recipe);
    }

    public void insertRecipeWithIngredients (Recipe recipe) {
        new insertAsyncTask(mRecipeDao).execute(recipe);
    }

    public void update (Recipe... recipes) {
        new updateAsyncTask(mRecipeDao).execute(recipes);
    }

    private static class loadAsyncTask extends AsyncTask<Void, Void, Recipe> {
        private RecipeDao mAsyncTaskDao;

        public AsyncResponse delegate = null;

        @Override
        protected Recipe doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Recipe recipe) {
            super.onPostExecute(recipe);
            delegate.processFinish(recipe);
        }

        private LiveData<Recipe> getRecipe(int id) {
            return mAsyncTaskDao.getRecipeWithIngredients(id);
        }
    }

    private static class insertAsyncTask extends AsyncTask<Recipe, Void, Void> {

        private RecipeDao mAsyncTaskDao;

        insertAsyncTask(RecipeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Recipe... params) {
            //mAsyncTaskDao.insertRecipe(params[0]);
            mAsyncTaskDao.insertRecipeWithIngredients(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private RecipeDao mAsyncTaskDao;

        updateAsyncTask(RecipeDao dao) {this.mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(Recipe... params) {
            mAsyncTaskDao.updateRecipeWithIngredients(params[0]);
            return null;
        }
    }

}
