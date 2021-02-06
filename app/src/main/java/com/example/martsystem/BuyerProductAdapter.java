package com.example.martsystem;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.IOException;

public class BuyerProductAdapter extends FirebaseRecyclerAdapter<Product, BuyerProductAdapter.BuyerProductViewHolder> {
    Context context;

    public BuyerProductAdapter(@NonNull FirebaseRecyclerOptions<Product> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull BuyerProductViewHolder holder, int position, @NonNull Product model) {
        holder.tvProductName.setText(model.getProductName());
        holder.tvProductCategory.setText(model.getProductCategory());
        holder.tvProductPrice.setText(model.getProductPrice());
        holder.tvProductQuantity.setText(model.getProductQuantity());
        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(holder.itemView.getContext(), BuyActivity.class);
                holder.itemView.getContext().startActivity(newIntent);
                /*DialogPlus dialogPlus = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.buy_item_dialog))
                        .setGravity(Gravity.FILL_VERTICAL)
                        .setMargin(20, 0, 20, 0)
                        .setExpanded(true)
                        .create();
                View buyProductView = dialogPlus.getHolderView();
                TextView tvProductName, tvProductCategory, tvProductPrice, tvProductQuantity, tvProductSellerName, tvProductSellerContact, tvProductDesc, tvBuyerProductQuantity;
                AppCompatButton btnIncrement, btnDecrement, btnAddToCart;
                int initialProductQuantity = 0;
                tvProductName = buyProductView.findViewById(R.id.tvProductName);
                tvProductCategory = buyProductView.findViewById(R.id.tvProductCategory);
                tvProductPrice = buyProductView.findViewById(R.id.tvProductPrice);
                tvProductQuantity = buyProductView.findViewById(R.id.tvProductQuantity);
                tvProductSellerName = buyProductView.findViewById(R.id.tvProductSellerName);
                tvProductSellerContact = buyProductView.findViewById(R.id.tvProductSellerContact);
                tvProductDesc = buyProductView.findViewById(R.id.tvProductDesc);
                tvBuyerProductQuantity = buyProductView.findViewById(R.id.tvBuyerProductQuantity);
                btnIncrement = buyProductView.findViewById(R.id.btnIncrement);
                btnDecrement = buyProductView.findViewById(R.id.btnDecrement);
                btnAddToCart = buyProductView.findViewById(R.id.btnAddToCart);
                tvProductName.setText(model.getProductName());
                tvProductCategory.setText(model.getProductCategory());
                tvProductPrice.setText(model.getProductPrice());
                tvProductQuantity.setText(model.getProductQuantity());
                tvProductDesc.setText(model.getProductDesc());
                tvBuyerProductQuantity.setText(initialProductQuantity);
                dialogPlus.show();*/
                /*btnIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String stringProductQuantity = tvProductQuantity.getText().toString().trim();
                        int intProductQuantity = Integer.parseInt(stringProductQuantity);
                        if (intProductQuantity == initialProductQuantity || intProductQuantity > initialProductQuantity) {
                            tvBuyerProductQuantity.setText(initialProductQuantity + 1);
                        } else {
                            Toast.makeText(context, R.string.product_is_in_less_amount, Toast.LENGTH_LONG).show();
                        }
                    }
                });*/
                /*btnDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String stringValue = tvBuyerProductQuantity.getText().toString().trim();
                            int value = Integer.parseInt(stringValue);
                            tvBuyerProductQuantity.setText(value - 1);
                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });*/
            }
        });
    }

    @NonNull
    @Override
    public BuyerProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyer_single_item, parent, false);
        return new BuyerProductViewHolder(view);
    }

    public class BuyerProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductCategory, tvProductPrice, tvProductQuantity;
        AppCompatButton btnBuy;

        public BuyerProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductCategory = itemView.findViewById(R.id.tvProductCategory);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductQuantity = itemView.findViewById(R.id.tvProductQuantity);
            btnBuy = itemView.findViewById(R.id.btnBuy);
        }
    }
}