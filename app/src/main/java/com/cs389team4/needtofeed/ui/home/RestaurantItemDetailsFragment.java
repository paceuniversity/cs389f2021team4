package com.cs389team4.needtofeed.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Order;
import com.bumptech.glide.Glide;

import com.cs389team4.needtofeed.databinding.FragmentRestaurantItemDetailsBinding;
import com.cs389team4.needtofeed.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.NumberFormat;
import java.time.LocalTime;
import java.util.Currency;
import java.util.concurrent.atomic.AtomicInteger;

public class RestaurantItemDetailsFragment extends Fragment {
    private FragmentRestaurantItemDetailsBinding binding = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRestaurantItemDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageViewItem = binding.itemDetailsImage;
        TextView textViewItemName = binding.itemDetailsName;
        TextView textViewItemPrice = binding.itemDetailsPrice;

        RestaurantItemDetailsFragmentArgs args = RestaurantItemDetailsFragmentArgs.fromBundle(getArguments());

        String itemImage = args.getItemImage();
        Glide.with(view).load(itemImage).centerCrop().into(imageViewItem);

        String itemName = args.getItemName();
        textViewItemName.setText(itemName);

        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(Currency.getInstance("USD"));
        String itemPrice = format.format(args.getItemPrice());
        textViewItemPrice.setText(itemPrice);

        Button btnAddToCart = binding.itemDetailsAddButton;

        ImageButton btnQuantityAdd = binding.btnQuantityIncrease;
        ImageButton btnQuantityRemove = binding.btnQuantityDecrease;
        TextView textViewQuantity = binding.lblQuantity;

        AtomicInteger quantity = new AtomicInteger(1);

        btnQuantityAdd.setOnClickListener(v -> {
            quantity.getAndIncrement();
            textViewQuantity.setText(quantity.toString());
        });
        btnQuantityRemove.setOnClickListener(v -> {
            if (quantity.get() > 1) {
                quantity.getAndDecrement();
                textViewQuantity.setText(quantity.toString());
            }
        });

        btnAddToCart.setOnClickListener(v -> Amplify.API.query(
                ModelQuery.list(Order.class, Order.IS_ACTIVE.eq(true)),
                response -> {
                    // If no active order exists
                    if (response.getData().getItems().toString().equals("[]")) {

                        JsonObject orderContent = new JsonObject();
                        orderContent.add("quantity", new Gson().toJsonTree(quantity));
                        orderContent.add("price", new Gson().toJsonTree(itemPrice));

                        JsonObject orderDetailsJson = new JsonObject();
                        orderDetailsJson.add(itemName, orderContent);

                        Order todo = Order.builder()
                                .orderType("Delivery")
                                .estimatedTimeComplete(new Temporal.Time(String.valueOf(LocalTime.now())))
                                .orderTotal(0.99)
                                .orderItems(orderDetailsJson.toString())
                                .orderedBy("user_ordered")
                                .isActive(true)
                                .orderRestaurant("restaurant_name")
                                .orderRestaurantId("restaurant_id")
                                .build();

                        Amplify.API.mutate(ModelMutation.create(todo),
                                resp -> Log.i("MyAmplifyApp", "Todo with id: " + resp),
                                error -> Log.e("MyAmplifyApp", "Create failed", error)
                        );
                    } else {
                        Order existingOrder = response.getData().iterator().next();
                        JsonObject orderContent = new JsonObject();
                        orderContent.add("quantity", new Gson().toJsonTree(quantity));
                        orderContent.add("price", new Gson().toJsonTree(itemPrice));

                        JsonObject orderDetailsJson = JsonParser.parseString(existingOrder.getOrderItems()).getAsJsonObject();
                        orderDetailsJson.add(itemName, orderContent);

                        Utils.showMessage(getContext(), orderDetailsJson.toString());

                        Order orderUpdate = Order.builder()
                                .orderType(existingOrder.getOrderType())
                                .estimatedTimeComplete(existingOrder.getEstimatedTimeComplete())
                                .orderTotal(existingOrder.getOrderTotal())
                                .orderItems(orderDetailsJson.toString())
                                .orderedBy(existingOrder.getOrderedBy())
                                .isActive(existingOrder.getIsActive())
                                .orderRestaurant(existingOrder.getOrderRestaurant())
                                .orderRestaurantId(existingOrder.getOrderRestaurantId())
                                .id(existingOrder.getId())
                                .build();

                            Amplify.API.mutate(ModelMutation.update(orderUpdate),
                                    resp -> Log.i("MyAmplifyApp", "Todo with id: " + resp),
                                    error -> Log.e("MyAmplifyApp", "Create failed", error)
                            );
                    }
                },
                error -> Log.e("Failure updating order: ", error.getMessage())
        ));
    }
}
