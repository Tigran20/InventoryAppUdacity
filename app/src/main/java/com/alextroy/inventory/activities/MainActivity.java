package com.alextroy.inventory.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alextroy.inventory.R;
import com.alextroy.inventory.adapter.InventoryAdapter;
import com.alextroy.inventory.model.Data;
import com.alextroy.inventory.model.Product;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InventoryAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton actionButton;
    private TextView emptyView;

    private static List<Product> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = Data.getProductData(this);

        emptyView = findViewById(R.id.empty_view);
        recyclerView = findViewById(R.id.recycler_view);
        actionButton = findViewById(R.id.fab);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new InventoryAdapter(getApplicationContext(), list);
        recyclerView.setAdapter(adapter);

        if (list.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        actionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            startActivity(intent);
        });
    }
}
