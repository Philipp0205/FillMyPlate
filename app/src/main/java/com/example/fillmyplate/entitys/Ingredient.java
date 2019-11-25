package com.example.fillmyplate.entitys;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient_table", foreignKeys =
@ForeignKey(entity = Recipe.class, parentColumns = "uid", childColumns = "recipeId"))
public class Ingredient {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "recipeId", index = true)
    public int recipeId;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "amount")
    private String amount;
    @ColumnInfo(name = "unit")
    private String unit;

    public Ingredient(@NonNull String title, String amount, String unit, int recipeId) {
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
}
