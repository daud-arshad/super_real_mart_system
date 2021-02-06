package com.example.martsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class BuyerActivity extends AppCompatActivity {
    private TextView tvMode;
    private Switch sellerBuyerSwitch;
    private RecyclerView buyerRecyclerView;
    private BuyerProductAdapter buyerProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);
        init();
        buyerRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Products"), Product.class)
                .build();
        buyerProductAdapter = new BuyerProductAdapter(options, this);
        buyerRecyclerView.setAdapter(buyerProductAdapter);
        sellerBuyerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                } else {
                    Intent buyerIntent = new Intent(BuyerActivity.this, SellerActivity.class);
                    startActivity(buyerIntent);
                }
            }
        });
    }

    private void init() {
        tvMode = findViewById(R.id.tvMode);
        sellerBuyerSwitch = findViewById(R.id.SellerBuyerSwitch);
        buyerRecyclerView = findViewById(R.id.buyerRecyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        buyerProductAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        buyerProductAdapter.stopListening();
    }
}