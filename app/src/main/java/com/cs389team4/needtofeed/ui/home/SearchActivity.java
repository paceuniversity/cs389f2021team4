package com.cs389team4.needtofeed.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Restaurant;
import com.cs389team4.needtofeed.R;
import com.cs389team4.needtofeed.utils.RecyclerViewAdapter;

import org.bouncycastle.util.test.Test;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);
        Intent intent = getIntent();
        String test = intent.getStringExtra(RestaurantFragment.EXTRA_MESSAGE);

        Log.d("Test", "onCreate: " + test);
    }
}