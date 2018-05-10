package com.alextroy.inventory.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alextroy.inventory.R;
import com.alextroy.inventory.model.Data;
import com.alextroy.inventory.model.Product;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String DATA = "data";

    private Product product;
    private boolean isNewProduct = true;

    private EditText productTitle;
    private EditText productPrice;
    private EditText productQuantity;
    private EditText productSupplierName;
    private EditText productSupplierPhoneNumber;

    private FloatingActionButton deleteProduct;
    private FloatingActionButton saveData;
    private FloatingActionButton callSupplier;

    private ImageView quantityPlus;
    private ImageView quantityMinus;

    private int currentQuantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        init();
        intentData();
    }

    private void init() {
        productTitle = findViewById(R.id.product_title);
        productPrice = findViewById(R.id.product_price);
        productQuantity = findViewById(R.id.quantity);
        productSupplierName = findViewById(R.id.supplier_name);
        productSupplierPhoneNumber = findViewById(R.id.supplier_phone_number);

        deleteProduct = findViewById(R.id.delete_product);
        saveData = findViewById(R.id.save_data);
        callSupplier = findViewById(R.id.call_supplier);
        quantityPlus = findViewById(R.id.plus);
        quantityMinus = findViewById(R.id.minus);

        deleteProduct.setOnClickListener(this);
        quantityMinus.setOnClickListener(this);
        quantityPlus.setOnClickListener(this);
        saveData.setOnClickListener(this);
        callSupplier.setOnClickListener(this);
    }

    private void intentData() {
        Intent intent = getIntent();
        if (intent != null) {
            this.product = (Product) intent.getSerializableExtra(DATA);
            if (product != null) {
                isNewProduct = false;
                productTitle.setText(product.getName());
                productPrice.setText(product.getPrice());
                productSupplierName.setText(product.getSupplierName());
                productSupplierPhoneNumber.setText(product.getSupplierPhone());
                currentQuantity = product.getQuantity();
                productQuantity.setText(String.valueOf(currentQuantity));
            } else {
                deleteProduct.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void getToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_product:
                if (product != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                    builder
                            .setTitle(R.string.delete_product_alert)
                            .setMessage(R.string.delete_product_alert_quest)
                            .setCancelable(false)
                            .setPositiveButton(R.string.alert_no_answer, (dialog, i) -> dialog.cancel())
                            .setNegativeButton(R.string.alert_yes_answer, (dialog, id) -> {
                                dialog.cancel();
                                Data.deleteData(product.getId());
                                finish();
                            });
                    builder.create().show();
                }
                break;
            case R.id.save_data:
                String title = productTitle.getText().toString();
                if (TextUtils.isEmpty(title)) {
                    getToast(getString(R.string.enter_product_title));
                    break;
                }

                String price = productPrice.getText().toString();
                if (TextUtils.isEmpty(price)) {
                    getToast(getString(R.string.enter_price));
                    break;
                }

                String supplierName = productSupplierName.getText().toString();
                if (TextUtils.isEmpty(supplierName)) {
                    getToast(getString(R.string.enter_supplier_name));
                    break;
                }

                String supplierPhone = productSupplierPhoneNumber.getText().toString();
                if (TextUtils.isEmpty(supplierPhone)) {
                    getToast(getString(R.string.enter_supplier_phone));
                    break;
                }

                String stringQuantity = productQuantity.getText().toString();
                if (TextUtils.isEmpty(String.valueOf(stringQuantity))) {
                    getToast(getString(R.string.enter_product_quantity));
                    break;
                }
                int quantity = Integer.parseInt(stringQuantity);
                if (isNewProduct) {
                    Data.insertData(title, price, quantity, supplierName, supplierPhone);
                } else {
                    Data.updateData(product.getId(), title, price, quantity, supplierName, supplierPhone);
                }
                finish();
                break;
            case R.id.plus:
                currentQuantity++;
                productQuantity.setText(String.valueOf(currentQuantity));
                break;
            case R.id.minus:
                currentQuantity--;
                if (currentQuantity < 0) {
                    currentQuantity = 0;
                }
                productQuantity.setText(String.valueOf(currentQuantity));
                break;
            case R.id.call_supplier:
                if (!TextUtils.isEmpty(productSupplierPhoneNumber.getText().toString())) {
                    Intent intentCall = new Intent(Intent.ACTION_DIAL);
                    intentCall.setData(Uri.parse("tel:" + productSupplierPhoneNumber.getText().toString()));
                    startActivity(intentCall);
                } else {
                    getToast(getString(R.string.enter_supplier_phone_number));
                }
                break;
        }
    }


}
