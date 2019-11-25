package com.example.fillmyplate.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fillmyplate.R;
import com.example.fillmyplate.entitys.Ingredient;

import java.util.LinkedList;
import java.util.List;

/**
 * Implements an Adapter for a RecyclerView.
 *
 */
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {
    private static final String TAG = "IngredientsAdapter";

    private List<String> mIngredientsList;
    private List<String> mIngredientAmountsList;
    private List<String> mUnitList;
    private List<String> mEmojiList;
    private LayoutInflater mInflater;


    private Context context;

    class IngredientsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener  {

        public final TextView ingredientItemView;
        public final TextView amountItemTextView;
        public final TextView unitTextView;
        public final TextView emojiItemView;
        final IngredientsAdapter mAdapter;

        /**
         * Creates a new custom view holder to hold the view to display in
         * the RecyclerView.
         *
         * @param itemView The view in which to display the data.
         * @param adapter The adapter that manages the the data and views
         *                for the RecyclerView.
         */
        public IngredientsViewHolder(View itemView, IngredientsAdapter adapter) {
            super(itemView);
            ingredientItemView = itemView.findViewById(R.id.ingredient);
            amountItemTextView = itemView.findViewById(R.id.amountItemTextView);
            unitTextView = itemView.findViewById(R.id.UnitItemTextView);
            emojiItemView = itemView.findViewById(R.id.ingredientIcon);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();

            // Use that to access the affected item in mWordList.
            String element = mIngredientsList.get(mPosition);
            // Change the word in the mWordList.

            mIngredientsList.set(mPosition, "Clicked! " + element);
            // Notify the adapter, that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();

        }
    }

    // Constructor
    public IngredientsAdapter(Context context, List<String> ingredientsList, List<String> ingredientAmountsList, List<String> unitList,
                              List<String> emojiList) {
        mInflater = LayoutInflater.from(context);
        this.mIngredientsList = ingredientsList;
        this.mUnitList = unitList;
        this.mIngredientAmountsList = ingredientAmountsList;

        this.mEmojiList = emojiList;

        this.context = context;
    }
    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to
     * represent an item.
     *
     * This new ViewHolder should be constructed with a new View that can
     * represent the items of the given type. You can either create a new View
     * manually or inflate it from an XML layout file.
     *
     * The new ViewHolder will be used to display items of the adapter using
     * onBindViewHolder(ViewHolder, int, List). Since it will be reused to
     * display different items in the data set, it is a good idea to cache
     * references to sub views of the View to avoid unnecessary findViewById()
     * calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after
     *                 it is bound to an adapter position.
     * @param viewType The view type of the new View. @return A new ViewHolder
     *                 that holds a View of the given view type.
     */
    public IngredientsAdapter.IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Infalte an item view
        View mIntredientView = mInflater.inflate(
                R.layout.ingridientlist_item, parent, false);
        return new IngredientsViewHolder(mIntredientView, this);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the ViewHolder.itemView to
     * reflect the item at the given position.
     *
     * @param holder   The ViewHolder which should be updated to represent
     *                 the contents of the item at the given position in the
     *                 data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(IngredientsAdapter.IngredientsViewHolder holder, int position) {
        //Retreive the data for that position

        String mCurrentIngredient = mIngredientsList.get(position);
        String mCurrentAmount = mIngredientAmountsList.get(position);
        String mCurrentUnit = mUnitList.get(position);
        String mCurrentEmoji = mEmojiList.get(position);

        // Add the data to the view holder
        holder.ingredientItemView.setText(mCurrentIngredient);
        holder.amountItemTextView.setText(mCurrentAmount);
        holder.unitTextView.setText(mCurrentUnit);
        holder.emojiItemView.setText(mCurrentEmoji);

       // holder.amountItemTextView.setText("");
    }

    /**
     * holde
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mIngredientsList.size();
    }

    // @TODO Wird wahrscheinlich nicht gebraucht
    void setIngredients(LinkedList<String> ingredients) {
        mIngredientsList = ingredients;
        notifyDataSetChanged();

    }



}
