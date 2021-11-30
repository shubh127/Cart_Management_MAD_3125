package com.example.assignment6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

public class CartActivity extends AppCompatActivity implements CartItemAdapter.OnItemRemove {
    private HashMap<Product, Integer> cartProducts = new HashMap<>();
    private RecyclerView rvProducts;
    private TextView tvNoProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getDataFromIntent();
        initViews();
        configViews();
    }

    private void configViews() {
        if (cartProducts != null && cartProducts.size() > 0) {
            rvProducts.setLayoutManager(new LinearLayoutManager(this));
            rvProducts.setAdapter(new CartItemAdapter(cartProducts, this));
            tvNoProducts.setVisibility(View.GONE);
            rvProducts.setVisibility(View.VISIBLE);
        } else {
            tvNoProducts.setVisibility(View.VISIBLE);
            rvProducts.setVisibility(View.GONE);
        }
    }

    private void initViews() {
        rvProducts = findViewById(R.id.rv_products);
        tvNoProducts = findViewById(R.id.tv_no_products);
    }

    private void getDataFromIntent() {
        if (getIntent().getExtras() != null &&
                getIntent().getSerializableExtra("CART_PRODUCTS") != null) {
            cartProducts =
                    (HashMap) getIntent().getSerializableExtra("CART_PRODUCTS");
        }
    }

    @Override
    public void onItemRemoveClick(Product product) {
        cartProducts.remove(product);
        rvProducts.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("CART_PRODUCTS", cartProducts);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("CART_PRODUCTS", cartProducts);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
        return true;
    }
}