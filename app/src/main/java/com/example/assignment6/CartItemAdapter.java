package com.example.assignment6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    private final HashMap<Product, Integer> products;
    private final OnItemRemove listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvProductName;
        private final TextView tvProductQuantity;
        private final TextView tvRemoveFromCart;

        public ViewHolder(View view) {
            super(view);
            tvProductName = view.findViewById(R.id.tv_product_name);
            tvProductQuantity = view.findViewById(R.id.tv_product_quantity);
            tvRemoveFromCart = view.findViewById(R.id.tv_remove_product);
        }
    }

    public CartItemAdapter(HashMap<Product, Integer> products, OnItemRemove listener) {
        this.products = products;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cart_item_view, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Product product = (Product) products.keySet().toArray()[position];
        viewHolder.tvProductName.setText(String.format("Product Name: %s", product.getName()));
        viewHolder.tvProductQuantity.setText(String.format("Quantity: %s", products.get(product)));
        viewHolder.tvRemoveFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemRemoveClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    interface OnItemRemove {
        void onItemRemoveClick(Product product);
    }
}