package com.example.assignment6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener,
        View.OnClickListener {
    private Spinner spProducts;
    List<String> productNames = new ArrayList<>();
    List<Product> productList = new ArrayList<>();
    HashMap<Product, Integer> cartProducts = new HashMap<>();
    private ImageView ivProduct;
    private TextView tvPrice;
    private EditText etQuantity;
    private Button btnAddToCart;
    private TextView tvFinalPrice;
    private Button btnViewCart;
    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            handleDataFromCartActivity(data);
                        }
                    }
                });

        populateList();
        initViews();
        configViews();
    }

    private void handleDataFromCartActivity(Intent data) {
        cartProducts = (HashMap) data.getSerializableExtra("CART_PRODUCTS");
        updateFinalPrice();
    }

    private void populateList() {
        productList.add(new Product("TV", R.drawable.tv, 200));
        productList.add(new Product("PC", R.drawable.pc, 350));
        productList.add(new Product("Mobile", R.drawable.mobile, 150));
        productList.add(new Product("Laptop", R.drawable.laptop, 325));
        productList.add(new Product("Tablet", R.drawable.tablet, 100));
        productList.add(new Product("HeadPhone", R.drawable.headphone, 75));

        for (Product product : productList) {
            productNames.add(product.getName());
        }
    }

    private void initViews() {
        spProducts = findViewById(R.id.sp_products);
        ivProduct = findViewById(R.id.iv_product);
        tvPrice = findViewById(R.id.tv_price);
        etQuantity = findViewById(R.id.et_quantity);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        tvFinalPrice = findViewById(R.id.tv_final_price);
        btnViewCart = findViewById(R.id.btn_view_cart);
    }

    private void configViews() {
        btnAddToCart.setOnClickListener(this);
        spProducts.setOnItemSelectedListener(this);
        btnViewCart.setOnClickListener(this);

        ArrayAdapter<String> aa = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, productNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProducts.setAdapter(aa);

        ivProduct.setImageDrawable(ContextCompat.getDrawable(this,
                productList.get(0).getImgID()));
        tvPrice.setText(String.format("Price: $%s", productList.get(0).getPrice()));
        updateFinalPrice();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ivProduct.setImageDrawable(ContextCompat.getDrawable(this,
                productList.get(i).getImgID()));
        tvPrice.setText(String.format("Price: %s", productList.get(i).getPrice()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //no op
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add_to_cart) {
            onAddToCartClick();
        } else if (view.getId() == R.id.btn_view_cart) {
            onViewCartClick();
        }
    }

    private void onViewCartClick() {
        Intent intent = new Intent(this, CartActivity.class);
        intent.putExtra("CART_PRODUCTS", cartProducts);
        launcher.launch(intent);
    }

    private void onAddToCartClick() {
        if (TextUtils.isEmpty(etQuantity.getText()) ||
                Integer.parseInt(etQuantity.getText().toString()) < 1) {
            Toast.makeText(this,
                    "Invalid quantity!!! Please check once and try again!!!",
                    Toast.LENGTH_SHORT).show();
        } else {
            AddToCart();
            etQuantity.getText().clear();
        }
    }

    private void AddToCart() {
        cartProducts.put(productList.get(spProducts.getSelectedItemPosition()),
                Integer.parseInt(etQuantity.getText().toString()));
        Toast.makeText(this, "" + cartProducts.size(), Toast.LENGTH_SHORT).show();
        updateFinalPrice();
    }

    private void updateFinalPrice() {
        double finalPrice = 0;
        for (Map.Entry<Product, Integer> product : cartProducts.entrySet()) {
            finalPrice = finalPrice +
                    (((Product) product.getKey()).getPrice() * product.getValue());
        }
        tvFinalPrice.setText(String.format("Final Price is: %s", finalPrice));
    }
}