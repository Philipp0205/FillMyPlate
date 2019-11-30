package com.example.fillmyplate.entitys;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.fillmyplate.activities.AddRecipeActivity;
import com.example.fillmyplate.activities.Converters;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity(tableName = "recipe_table")
public class Recipe {

    @PrimaryKey
    private int uid;
    @ColumnInfo(name = "title")
    private String title;
    //private LinkedList<String> ingredients;

    @Ignore
    private List<Ingredient> ingredientList;

    public Recipe(@NonNull int uid, String title, List<Ingredient> ingredientList) {
        this.title = title;
        this.ingredientList = ingredientList;
        this.uid = uid;
    }

    public Recipe() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public List<Ingredient>  getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

}
