package com.example.fillmyplate.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fillmyplate.entitys.Ingredient;

import java.util.List;

@Dao
public interface IngredientsDao {
    @Insert
    void insert(Ingredient... ingredients);

    @Update
    void update(Ingredient... ingredients);

    @Delete
    void delete(Ingredient... ingredients);

    @Query("DELETE FROM ingredient_table")
    void deleteAll();

    @Query("SELECT * from ingredient_table ORDER BY title ASC")
    LiveData<List<Ingredient>> getAllIngredients();

    @Query("SELECT * FROM ingredient_table WHERE recipeId=:recipeId")
    List<Ingredient> findIngredientsForUser(int recipeId);
}
