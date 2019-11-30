package com.example.fillmyplate.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.example.fillmyplate.R;
import com.example.fillmyplate.entitys.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.AdapterListener  {

    public static final String TAG = "MainAcitivity";
    public static final String EXTRA_RECIPETITLE = "com.example.fillmyplate.REPLY";
    public static final String EXTRA_UID = "com.example.fillmyplate.EXTRA_UID";

    private static final int NEW_RECIPE_ACTIVITY_REQUEST_CODE = 1;
    private static final int KNOWN_RECIPE_ACTIVITY_REQUEST_CODE = 1;

    private static final String EXTRA_REPLY = "com.example.fillmyplate.REPLY";

    private static final String ACTION_UPDATE_NOTIFICATION = "com.android.example.fillmyplate.ACTION_UPDATE_NOTIFICATION";
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;


    private RecyclerView mRecyclerView;
    
    private RecipeViewModel mRecipeViewmodel; 

    private RecyclerView.LayoutManager layoutManager;

    private NotificationManager mNotifyManager;

    private NotificationReceiver mReceiver = new NotificationReceiver();


    //private final List<String> mTitleList = new LinkedList<>();

    //NEU for adapter
    private List<String> recipeDataList = new ArrayList<>();

    static RecipeRoomDatabase db;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RECYCLER VIEW STUFF
        mRecyclerView = findViewById(R.id.recycler_view1);

        mRecyclerView.setHasFixedSize(true);

        // user linerar layout manager
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // specify an adapter

        final RecipeAdapter recipeAdapter = new RecipeAdapter(this);
        recipeAdapter.setAdapterListener(MainActivity.this);
        //mAdapter = new RecipeAdapter(this, mTitleList);
        mRecyclerView.setAdapter(recipeAdapter);

        mRecipeViewmodel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        mRecipeViewmodel.getAllRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                Log.d(TAG, "onChanged: " + recipes.toString());
                for (Recipe rec : recipes) {
                    Log.d(TAG, "onChanged: " + rec.getTitle());
                    Log.d(TAG, "onChanged: recipe id " + rec.getUid());
                }
                recipeAdapter.setRecipes(recipes);
            }
        });
        



        // DB
        db = Room.databaseBuilder(getApplicationContext(), RecipeRoomDatabase.class, "appdb").build();

        // NOTIFICATIONS
        // Create the notification channel.
        createNotificationChannel();

        // Register the broadcast receiver to receive the update action from
        // the notification.
        registerReceiver(mReceiver,
                new IntentFilter(ACTION_UPDATE_NOTIFICATION));



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void
            onClick(View view) {
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
        } else if (id == R.id.pnotification) {
            sendNotification();
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
            //Recipe rec = new Recipe(data.getStringExtra(AddRecipeActivity.EXTRA_REPLY));
            //mRecipeViewmodel.insertRecipe(rec);

        } else if (requestCode == KNOWN_RECIPE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

        } else {

            Toast.makeText(
                    getApplicationContext(),
                    "saved",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(int id) {
        Log.d(TAG, "onClick: Id " + id);
        final Intent openIntent = new Intent(this, AddRecipeActivity.class);
        final Bundle extras = new Bundle();

        mRecipeViewmodel.getrecipeById(++id).observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                extras.putString(EXTRA_RECIPETITLE, recipe.getTitle());
                extras.putInt(EXTRA_UID, recipe.getUid());

                openIntent.putExtras(extras);
                startActivityForResult(openIntent, KNOWN_RECIPE_ACTIVITY_REQUEST_CODE);
            }
            });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.pnotification:
                sendNotification();
        }
        return super.onContextItemSelected(item);
    }



    private void sendNotification() {

        // Sets up the pending intent to update the notification.
        // Corresponds to a press of the Update Me! button.
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);

        // Build the notification with all of the parameters using helper
        // method.
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        // Add the action button using the pending intent.
        notifyBuilder.addAction(R.drawable.ic_notification,
                getString(R.string.update), updatePendingIntent);

        // Deliver the notification.
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        // Enable the update and cancel buttons but disables the "Notify
        // Me!" button.
        setNotificationButtonState(false, true, true);


    }


    /**
     * Helper method that builds the notification.
     *
     * @return NotificationCompat.Builder: notification build with all the
     * parameters.
     */
    private NotificationCompat.Builder getNotificationBuilder() {

        // Set up the pending intent that is delivered when the notification
        // is clicked.
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity
                (this, NOTIFICATION_ID, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the notification with all of the parameters.
        NotificationCompat.Builder notifyBuilder = new NotificationCompat
                .Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true).setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        return notifyBuilder;
    }

    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            getString(R.string.notification_channel_name),
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    (getString(R.string.notification_channel_description));

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    /**
     * OnClick method for the "Update Me!" button. Updates the existing
     * notification to show a picture.
     */
    public void updateNotification() {

        // Load the drawable resource into the a bitmap image.
        Bitmap androidImage = BitmapFactory
                .decodeResource(getResources(), R.drawable.ic_notification);

        // Build the notification with all of the parameters using helper
        // method.
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        // Update the notification style to BigPictureStyle.
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle(getString(R.string.notification_updated)));

        // Deliver the notification.
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        // Disable the update button, leaving only the cancel button enabled.
        setNotificationButtonState(false, false, true);
    }

    /**
     * OnClick method for the "Cancel Me!" button. Cancels the notification.
     */
    public void cancelNotification() {
        // Cancel the notification.
        mNotifyManager.cancel(NOTIFICATION_ID);

        // Reset the buttons.
        setNotificationButtonState(true, false, false);
    }

    /**
     * Helper method to enable/disable the buttons.
     *
     * @param isNotifyEnabled, boolean: true if notify button enabled
     * @param isUpdateEnabled, boolean: true if update button enabled
     * @param isCancelEnabled, boolean: true if cancel button enabled
     */
    void setNotificationButtonState(Boolean isNotifyEnabled, Boolean
            isUpdateEnabled, Boolean isCancelEnabled) {
    }

    /**
     * The broadcast receiver class for notifications.
     * Responds to the update notification pending intent action.
     */
    public class NotificationReceiver extends BroadcastReceiver {

        public NotificationReceiver() {
        }

        /**
         * Receives the incoming broadcasts and responds accordingly.
         *
         * @param context Context of the app when the broadcast is received.
         * @param intent The broadcast intent containing the action.
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            // Update the notification.
            updateNotification();
        }
    }


//Log.d(TAG, "onClick: recipe " + recipe.getValue().toString());



        //Log.d(TAG, "onClick: " +recipe.getValue().get(0).getTitle());






    @Override
    public void onClick(ViewModel object) {

    }
}
