package com.example.fillmyplate.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fillmyplate.entitys.Ingredient;
import com.example.fillmyplate.entitys.Recipe;

import java.util.List;

import retrofit2.http.DELETE;


@Dao
public abstract class RecipeDao {

    @Insert
    public abstract void insertRecipe(Recipe recipe);

    @Insert
    public abstract void insertIngredientList(List<Ingredient> ingredients);

    @Update
    public abstract void updateRecipe(Recipe... recipes);



    @Query("DELETE FROM recipe_table")
    public abstract void deleteAll();

    @Query("DELETE FROM ingredient_table ")
    public abstract void deleteAllIngredients();

    @Query("SELECT * FROM recipe_table ORDER BY title ASC")
    public abstract LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM recipe_table WHERE uid=:uid")
    public abstract LiveData<Recipe> getRecipeById(int uid);

    @Query("SELECT * FROM ingredient_table WHERE recipeId=:recipeId")
    public abstract LiveData<List<Ingredient>> getIngredientsWithRecipeId(int recipeId);

    // In this case the uid is the userId!
    @Query("SELECT * FROM ingredient_table WHERE uid=:uid")
    public abstract List<Ingredient> getIngredientList(int uid);

    public void insertRecipeWithIngredients(Recipe recipe) {
        List<Ingredient> ingredients = recipe.getIngredientList();
        for (int i = 0; i < ingredients.size(); i++) {
            ingredients.get(i).setUid(recipe.getUid());
        }
        insertIngredientList(ingredients);
        insertRecipe(recipe);

    }

    public void updateRecipeWithIngredients(Recipe recipe) {
        deleteAllIngredients();

        insertRecipeWithIngredients(recipe);



    }

    public LiveData<Recipe> getRecipeWithIngredients(int id) {
        LiveData<Recipe> recipe = getRecipeById(id);
        List<Ingredient> ingredients = getIngredientList(id);
        recipe.getValue().setIngredientList(ingredients);
        return recipe;
    }



}
