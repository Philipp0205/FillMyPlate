package com.example.fillmyplate.entitys;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.fillmyplate.activities.Converters;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity(tableName = "recipe_table")
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "title")
    private String title;
    //private LinkedList<String> ingredients;

    /*
    @TypeConverters(Converters.class)
    @ColumnInfo(name = "ingredients")
    private List<String> ingredients;


     */
    public Recipe(@NonNull String title) {
        this.title = title;
        //this.ingredients = ingredients;
    }

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

    /*
    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

     */



}
