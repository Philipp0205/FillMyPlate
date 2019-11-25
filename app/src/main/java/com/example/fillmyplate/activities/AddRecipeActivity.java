package com.example.fillmyplate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fillmyplate.R;
import com.example.fillmyplate.entitys.Ingredient;
import com.example.fillmyplate.entitys.Recipe;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.LinkedList;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public final static String TAG = "AddRecipeActivity";

    public static final String EXTRA_REPLY = "com.example.fillmyplate.REPLY";
    private static final String EXTRA_AMOUNTS = "com.example.fillmyplate.AMOUNTS";
    private static final String EXTRA_RECIPE_TITLE = "com.example.fillmyplate.RECIPE_TITLE";
    private static final String EXTA_INGREDIENTS = "com.example.fillmyplate.INGREDIENTS";


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    private String mRecipeTitle;
    private final List<String> mIngredientsList = new LinkedList<>();
    private final List<String> mAmountList = new LinkedList<>();
    private final List<String> mUnitList = new LinkedList<>();
    private final List<String> mEmojiList = new LinkedList<>();

    EditText mIngredientEditText;
    EditText mAmountEditText;
    EditText mRecipeTitleEditText;
    Spinner mSpinner;

    String mUnit;

    RecipeViewModel mRecipeViewModel;
    IngredientViewModel mIngredientsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        //Buttons and Edit Text fields.
        mIngredientEditText = findViewById(R.id.ingredientsEditText);

        mRecyclerView = findViewById(R.id.IngredientsRecyclerView);
        mAmountEditText = findViewById(R.id.amoundEditText);
        mRecipeTitleEditText = findViewById(R.id.titleEditText);

        // RECYLCER VIEW
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new IngredientsAdapter(this, mIngredientsList, mAmountList, mUnitList, mEmojiList);
        mRecyclerView.setAdapter(mAdapter);

        // Get a new or existing ViewModel from the ViewModelProvider
        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        mIngredientsViewModel = ViewModelProviders.of(this).get(IngredientViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mRecipeViewModel.getAllRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                // Update the cached copy of the recipes in adapter
            }
        });


        //TITLE
        mRecipeTitle = mRecipeTitleEditText.getText().toString();

        // SPINNER
        mSpinner = findViewById(R.id.UnitSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        // EMOJIS
        //addIngredient();

        // DIVIDER
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, 1);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        // SWIPE TO DISMISS
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                //Log.d(TAG, "onSwiped: remove index" + mIngredientsList.get(swipeDir));
               //mIngredientsList.remove(swipeDir);

                Log.d(TAG, "onSwiped1: " + viewHolder.getAdapterPosition());

                mIngredientsList.remove(viewHolder.getAdapterPosition());
                mAmountList.remove(viewHolder.getAdapterPosition());
                mUnitList.remove(viewHolder.getAdapterPosition());
                mEmojiList.remove(viewHolder.getAdapterPosition());

               mRecyclerView.getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
                Log.d(TAG, "onSwiped: ");


            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    // Not used @TODO remove
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        mUnit = parent.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback.

    }

    public void addIngredient2(View view) {
        final int ingredientListSize = mIngredientsList.size();

        // Add a ingredient to the ingredient list
        mIngredientsList.add(mIngredientEditText.getText().toString());
        mAmountList.add(mAmountEditText.getText().toString());
        mUnitList.add(mSpinner.getSelectedItem().toString());

        //int  unicode = 0x1F60A;
        //mEmojiList.add(new String(Character.toChars(unicode)));

        String url = "https://emoji-api.com/emojis?search=" + mIngredientEditText.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse1: " + response);

                        // Parse response
                        try {
                            JSONArray jArray = new JSONArray(response);
                            Log.d(TAG, "onResponse2: " + jArray.getJSONObject(0));
                            int codepoint = Integer.parseInt(jArray.getJSONObject(0).getString("codePoint"),16);
                            char[] ch = Character.toChars(codepoint);
                            Log.d(TAG, "onResponse: 6 " + String.valueOf(ch));
                            Log.d(TAG, "onResponse3: " + jArray.getJSONObject(0).getString("codePoint"));
                            //mEmojiList.add(String.valueOf(ch));
                            mEmojiList.add(String.valueOf(ch));
                            // Notify adapter
                            mRecyclerView.getAdapter().notifyItemInserted(ingredientListSize);
                        } catch (JSONException e) {
                            // If there is no emoji found just add an apple.
                           // mEmojiList.add(null);🍎🍎
                            int codepoint = Integer.parseInt("1F34E", 16);
                            char[] ch = Character.toChars(codepoint);
                            mEmojiList.add(String.valueOf(ch));
                            mRecyclerView.getAdapter().notifyItemInserted(ingredientListSize);
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: VolleyError");
            }
        });

        queue.add(request);
    }

    public void saveRecipe(View view) {


        // Save data in DB
        Recipe recipe = new Recipe(mRecipeTitleEditText.getText().toString());
        mRecipeViewModel.insert(recipe);

        mRecipeViewModel.getAllRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                Log.d(TAG, "onChanged: recipes " + recipes.toString());

                int uid = recipes.get(0).getUid();
                Log.d(TAG, "onChanged: current uid is " + uid);

                for (String ing : mIngredientsList) {
                    Ingredient ingredient = new Ingredient(mIngredientsList.get(uid), mAmountList.get(uid), mUnitList.get(uid), uid);
                    Log.d(TAG, "saveRecipe ingredients: " + mIngredientsList.get(uid) + " " + mAmountList.get(uid) + " " + mUnitList.get(uid) + " " + uid);
                    mIngredientsViewModel.insert(ingredient);
                }


            }
        });


        // Send data to MainActivity
        Log.d(TAG, "saveRecipe: ");
        Intent replyIntent = new Intent();
        //Bundle extras = new Bundle();
        //extras.putString(EXTRA_RECIPE_TITLE, mRecipeTitle);
        //extras.putStringArray(EXTRA_AMOUNTS, (String[]) mAmountList.toArray());
        //extras.putStringArray(EXTA_INGREDIENTS, (String[]) mIngredientsList.toArray());
        //replyIntent.putExtra(EXTRA_REPLY,extras);
        if(TextUtils.isEmpty(mRecipeTitleEditText.getText().toString())) {
            Log.d(TAG, "saveRecipe: Result canceled" );
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            Log.d(TAG, "saveRecipe: Result ok " + mRecipeTitleEditText.getText());
            replyIntent.putExtra(EXTRA_REPLY, mRecipeTitleEditText.getText().toString());
            setResult(RESULT_OK, replyIntent);
        }
        finish();

    }
}
