package com.example.fillmyplate.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import com.example.fillmyplate.R;
import com.example.fillmyplate.entitys.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainAcitivity";

    private static final int NEW_RECIPE_ACTIVITY_REQUEST_CODE = 1;

    private RecyclerView mRecyclerView;
    
    private RecipeViewModel mRecipeViewmodel; 

    private RecyclerView.LayoutManager layoutManager;

    //private final List<String> mTitleList = new LinkedList<>();

    //NEU for adapter
    private List<String> recipeDataList = new ArrayList<>();

    RecipeRoomDatabase db;






    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // RECYCLER VIEW STUFF
        mRecyclerView = findViewById(R.id.recycler_view1);

        mRecyclerView.setHasFixedSize(true);

        // user linerar layout manager
        // @TODO change later
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // specify an adapter

        final RecipeAdapter recipeAdapter = new RecipeAdapter(this);
        //mAdapter = new RecipeAdapter(this, mTitleList);
        mRecyclerView.setAdapter(recipeAdapter);

        mRecipeViewmodel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        mRecipeViewmodel.getAllRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                Log.d(TAG, "onChanged: " + recipes.toString());
                for (Recipe rec : recipes) {
                    Log.d(TAG, "onChanged: " + rec.getTitle());
                }
                recipeAdapter.setRecipes(recipes);
            }
        });

        



        // DB
        db = Room.databaseBuilder(getApplicationContext(), RecipeRoomDatabase.class, "appdb").build();



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
                startActivityForResult(intent, NEW_RECIPE_ACTIVITY_REQUEST_CODE);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");

//
        if (requestCode == NEW_RECIPE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: " + data.getStringExtra(AddRecipeActivity.EXTRA_REPLY));
           // mTitleList.add(data.getStringExtra(AddRecipeActivity.EXTRA_REPLY));
            Recipe rec = new Recipe(data.getStringExtra(AddRecipeActivity.EXTRA_REPLY));
            mRecipeViewmodel.insert(rec);

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "saved",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void loadDataFromDb() {



    }
}
