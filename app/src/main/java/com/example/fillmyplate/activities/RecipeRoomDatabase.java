package com.example.fillmyplate.activities;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.fillmyplate.db.IngredientsDao;
import com.example.fillmyplate.db.RecipeDao;
import com.example.fillmyplate.entitys.Ingredient;
import com.example.fillmyplate.entitys.Recipe;

@Database(entities = {Recipe.class, Ingredient.class}, version = 6, exportSchema = false)
public abstract class RecipeRoomDatabase extends RoomDatabase {

    public abstract RecipeDao recipeDao();
    public abstract IngredientsDao ingredientsDao();
    private static RecipeRoomDatabase INSTANCE;

    static RecipeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RecipeRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RecipeRoomDatabase.class, "word_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;


    }


    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                }
            };







}
