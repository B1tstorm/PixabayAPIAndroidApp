package de.fh_kiel.iue.mob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        ArrayList<String> testData = intent.getStringArrayListExtra("PICTURE_DATA");

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("test_data", testData);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, DetailsFragment.class, bundle)
                .commit();
    }
}
