package com.example.martsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BuyActivity extends AppCompatActivity {
    private TextView tvProductName, tvProductCategory, tvProductPrice, tvProductQuantity, tvProductSellerName, tvProductSellerContact, tvProductDesc, tvBuyerProductQuantity;
    private AppCompatButton btnIncrement, btnDecrement, btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        init();
        btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void init() {
        tvProductName = findViewById(R.id.tvProductName);
        tvProductCategory = findViewById(R.id.tvProductCategory);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvProductQuantity = findViewById(R.id.tvProductQuantity);
        tvProductSellerName = findViewById(R.id.tvProductSellerName);
        tvProductSellerContact = findViewById(R.id.tvProductSellerContact);
        tvProductDesc = findViewById(R.id.tvProductDesc);
        tvBuyerProductQuantity = findViewById(R.id.tvBuyerProductQuantity);
        btnIncrement = findViewById(R.id.btnIncrement);
        btnDecrement = findViewById(R.id.btnDecrement);
        btnAddToCart = findViewById(R.id.btnAddToCart);
    }
}