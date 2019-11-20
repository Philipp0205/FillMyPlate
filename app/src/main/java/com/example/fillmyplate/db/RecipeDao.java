package com.example.fillmyplate.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fillmyplate.entitys.Recipe;

import java.util.List;


@Dao
public interface RecipeDao {

    @Insert
    void insert(Recipe recipe);

    @Query("DELETE FROM recipe_table")
    void deleteAll();

    @Query("SELECT * from recipe_table ORDER BY title ASC")
    LiveData<List<Recipe>> getAllRecipes();



}
