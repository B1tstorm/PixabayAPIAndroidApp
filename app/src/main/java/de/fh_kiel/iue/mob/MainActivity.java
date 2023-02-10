package de.fh_kiel.iue.mob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SearchView;

import de.fh_kiel.iue.mob.models.DataContainer;


public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    GridView gridView;
    PicturesAdapter picturesAdapter;
    Boolean dataState = true;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.gridView);

        if (savedInstanceState != null) {
            dataState = savedInstanceState.getBoolean("Adapter");
        }

        // Ein Costumadapter welcher dem GridView die Bilder downloaded
        picturesAdapter = new PicturesAdapter(this, DataContainer.getImages(), dataState);
        gridView.setAdapter(picturesAdapter);

        // Activity wechseln beim antippen eines Items der GridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    showDetailsView(gridView, position);
                } else {
                    showDetailsFragment(gridView, position);
                }
            }
        });
    }

    // Für die Anzeige des Menüs
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // Für Buttons die im Menü liegen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Switch switchData = findViewById(R.id.switchData); // Funktioniert nicht weil der Switchbutton ein RelativeLayout ist und kein Switch
        switch (item.getItemId()) {
            case R.id.refreshButton:
                Log.v(TAG, "onOptionsItemSelected: Refresh tapped");
                onStartAsyncTask();
                return true;
            case R.id.switchData:
                DataContainer dataContainer = new DataContainer();
                dataContainer.deleteData();
                dataContainer.cancel(true);
                ActionMenuItemView switchBtn = findViewById(R.id.switchData);
                if (dataState) {
                    dataState = false;
                    switchBtn.setText("Off");
                } else {
                    dataState = true;
                    switchBtn.setText("On");
                }

                // Ein Costumadapter welcher dem GridView die Bilder rendert
                picturesAdapter = new PicturesAdapter(this, dataContainer.getImages(), dataState);
                gridView.setAdapter(picturesAdapter);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    // AsyncTask wird gestartet und der Suchbegriff übergeben
    public void onStartAsyncTask() {
        // Ein Costumadapter welcher dem GridView die Bilder downloaded
        picturesAdapter = new PicturesAdapter(this, DataContainer.getImages(), dataState);
        gridView.setAdapter(picturesAdapter);

        Context context = getApplicationContext();
        SearchView searchKey = findViewById(R.id.searchKey);
        DataContainer dataContainer = new DataContainer(gridView, picturesAdapter, context, searchKey.getQuery().toString(), dataState);

        Integer num = 100;
        Integer sleep = 200;
        dataContainer.execute(num, sleep);
    }


    // Portrait
    public void showDetailsView(View view, int position) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("PICTURE_DATA", DataContainer.getData(position));
        startActivity(intent);
    }
    // Landscape
    public void showDetailsFragment(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("test_data", DataContainer.getData(position));

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(   R.id.fragment_container_view, DetailsFragment.class, bundle)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(TAG, "onSaveInstanceState");
        outState.putBoolean("Adapter", dataState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v(TAG, "onRestoreInstanceState");
        dataState = savedInstanceState.getBoolean("Adapter");
    }
}
