package com.example.martsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;

public class SellerActivity extends AppCompatActivity {
    private AppCompatButton btnAddProduct;
    private RecyclerView categoryRecyclerView;
    private TextView tvMode;
    private ProductAdapter productAdapter;
    private Switch SellerBuyerSwitch;
    private final int PRODUCTDATAENTRY = 01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        init();
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Products"), Product.class)
                .build();
        productAdapter = new ProductAdapter(options, this);
        categoryRecyclerView.setAdapter(productAdapter);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addProductIntent = new Intent(SellerActivity.this, ProductDataCollectionActivity.class);
                startActivity(addProductIntent);
            }
        });
        SellerBuyerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                } else {
                    Intent sellerIntent = new Intent(SellerActivity.this, BuyerActivity.class);
                    startActivity(sellerIntent);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        productAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        productAdapter.stopListening();
    }

    private void init() {
        btnAddProduct = findViewById(R.id.btnAddProduct);
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        tvMode = findViewById(R.id.tvMode);
        SellerBuyerSwitch = findViewById(R.id.SellerBuyerSwitch);
    }
}