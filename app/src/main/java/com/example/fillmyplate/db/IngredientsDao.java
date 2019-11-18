package com.example.fillmyplate.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fillmyplate.entitys.Ingredient;

import java.util.List;

@Dao
public interface IngredientsDao {
    @Insert
    void insert(Ingredient ingredient);

    @Query("DELETE FROM ingredient_table")
    void deleteAll();

    @Query("SELECT * from ingredient_table ORDER BY title ASC")
    LiveData<List<Ingredient>> getAllIngredients();
}
