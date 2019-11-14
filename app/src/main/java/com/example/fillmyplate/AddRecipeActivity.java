package com.example.fillmyplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.LinkedList;

public class AddRecipeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public final static String TAG = "AddRecipeActivity";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private final LinkedList<String> mIngredientsList = new LinkedList<>();
    private final LinkedList<String> mAmountList = new LinkedList<>();
    private final LinkedList<String> mEmojiList = new LinkedList<>();

    EditText mIngredientEditText;
    EditText mAmountEditText;
    Spinner mSpinner;

    String mUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        //Buttons and Edit Text fields.
        mIngredientEditText = findViewById(R.id.IngredientsEditText);

        mRecyclerView = findViewById(R.id.IngredientsRecyclerView);
        mAmountEditText = findViewById(R.id.amoundEditText);

        // RECYLCER VIEW
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new IngredientsAdapter(this, mIngredientsList, mAmountList, mEmojiList);
        mRecyclerView.setAdapter(mAdapter);

        // SPINNER
        mSpinner = findViewById(R.id.UnitSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        // EMOJIS
        //addIngredient();

    }

    public void addIngredient(View view) {
        int ingredientListSize = mIngredientsList.size();
        // Add a ingredient to the ingredient list
        mIngredientsList.add(mIngredientEditText.getText().toString());
        mAmountList.add(mAmountEditText.getText().toString() + mSpinner.getSelectedItem().toString());

        //int  unicode = 0x1F60A;
        //mEmojiList.add(new String(Character.toChars(unicode)));
        // Notify the adapter
        mRecyclerView.getAdapter().notifyItemInserted(ingredientListSize);


        // Scroll to the bottom
        //mRecyclerView.smoothScrollToPosition(ingredientListSize);
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
        mAmountList.add(mAmountEditText.getText().toString() + mSpinner.getSelectedItem().toString());

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



}
