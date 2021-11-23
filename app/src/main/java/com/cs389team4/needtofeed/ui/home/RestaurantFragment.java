package com.cs389team4.needtofeed.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.cs389team4.needtofeed.databinding.FragmentRestaurantBinding;

public class RestaurantFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout swipeRefreshLayout;
    private FragmentRestaurantBinding binding = null;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRestaurantBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      
        AppCompatButton btnDelivery = binding.deliveryButton;
        AppCompatButton btnPickup = binding.pickupButton;
        SearchView searchView = binding.searchRestaurants;

        btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDelivery.setBackgroundColor(Color.DKGRAY);
                btnPickup.setBackgroundColor(Color.WHITE);
            }
        });

        btnPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPickup.setBackgroundColor(Color.DKGRAY);
                btnDelivery.setBackgroundColor(Color.WHITE);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Log.d("", "onQueryTextSubmit: " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("", "onQueryTextChange: " + newText);
                return false;
            }
        });

        swipeRefreshLayout = binding.swipeRefreshRestaurants;
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler(Looper.getMainLooper()).postDelayed(() ->
                swipeRefreshLayout.setRefreshing(false), 1000);
    }
}