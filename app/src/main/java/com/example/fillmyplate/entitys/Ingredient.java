package com.example.fillmyplate.entitys;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient_table")
public class Ingredient {
    @PrimaryKey
    private int uid;
    public int recipeId;
    private String title;
    @ColumnInfo(name = "amount")
    private String amount;
    @ColumnInfo(name = "unit")
    private String unit;

    public Ingredient(@NonNull int recipeId, String title, String amount, String unit) {
        this.title = title;
        this.amount = amount;
        this.unit = unit;
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String mAmount) {
        this.amount = mAmount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String mUnit) {
        this.unit = mUnit;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getRecipeId() {return recipeId;}
}






