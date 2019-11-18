package com.example.fillmyplate.entitys;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient_table")
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "amount")
    private int amount;
    @ColumnInfo(name = "unit")
    private String unit;

    public Ingredient(@NonNull String title, int amount, String unit) {
        this.title = title;
        this.amount = amount;
        this.unit = unit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int mAmount) {
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
