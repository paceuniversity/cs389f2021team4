package com.cs389team4.needtofeed.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.amplifyframework.datastore.generated.model.Restaurant;
import com.cs389team4.needtofeed.R;
import com.cs389team4.needtofeed.databinding.FragmentRestaurantBinding;
import com.cs389team4.needtofeed.models.RestaurantViewModel;
import com.cs389team4.needtofeed.utils.ListFragment;
import com.cs389team4.needtofeed.utils.Utils;
import com.cs389team4.needtofeed.utils.ViewModel;

import java.util.UUID;

public class RestaurantListFragment extends ListFragment<Restaurant> {
    @NonNull
    @Override
    public Restaurant createModel() {
        return Restaurant.builder()
                .name("a name")
                .category("a category")
                .location("a location")
                .timeOpen(null)
                .timeClose(null)
                .image(null)
                .build();
    }

    @NonNull
    @Override
    public Restaurant updateModel(Restaurant model) {
        return model.copyOfBuilder()
                .name(UUID.randomUUID().toString())
                .build();
    }

    @NonNull
    @Override
    public Class<? extends Restaurant> getModelClass() {
        return Restaurant.class;
    }

    @NonNull
    @Override
    public ViewModel<Restaurant> getViewModel(@NonNull Restaurant model) {
        return new RestaurantViewModel(model);
    }

    @Override
    public void onClick(Restaurant item) {
        Navigation.findNavController(getView()).navigate(R.id.action_navigate_home_to_restaurantMenuFragment);
    }

    @Override
    public boolean onLongClick(Restaurant item) {
        return false;
    }
}
