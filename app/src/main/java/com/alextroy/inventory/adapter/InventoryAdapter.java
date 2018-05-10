package com.alextroy.inventory.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alextroy.inventory.R;
import com.alextroy.inventory.activities.DetailsActivity;
import com.alextroy.inventory.model.Data;
import com.alextroy.inventory.model.Product;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

    private Context context;
    private List<Product> list;
    public static final String DATA = "data";

    public InventoryAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inventory_item, parent, false);
        return new InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        final Product product = list.get(position);

        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice());
        holder.productQuantity.setText(String.valueOf(product.getQuantity()));
        if (holder.productQuantity.getText().toString().equals("0")) {
            holder.saleButton.setVisibility(View.INVISIBLE);
        }
        holder.saleButton.setOnClickListener(v -> {
            int currentQuantity = product.getQuantity();
            currentQuantity--;
            if (currentQuantity < 0) {
                currentQuantity = 0;
                holder.saleButton.setVisibility(View.INVISIBLE);
            }
            Data.updateData(product.getId(), product.getName(), product.getPrice(), currentQuantity, product.getSupplierName(), product.getSupplierPhone());
            Data.getProductData(context);
            notifyItemChanged(position);
        });
        holder.cardView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(DATA, product);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InventoryViewHolder extends RecyclerView.ViewHolder {

        private TextView productName;
        private TextView productPrice;
        private TextView productQuantity;
        private ImageView saleButton;
        private CardView cardView;

        public InventoryViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            saleButton = itemView.findViewById(R.id.sale_button);
            cardView = itemView.findViewById(R.id.card);
        }
    }
}
