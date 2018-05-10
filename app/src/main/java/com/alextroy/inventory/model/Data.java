package com.alextroy.inventory.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private static InventoryDbHelper dbHelper;
    private static Data data;
    private static List<Product> list;

    public Data() {
    }

    public static List<Product> getProductData(Context context) {
        if (data == null) {
            data = new Data();
        }
        if (list == null) {
            list = new ArrayList<>();
        } else {
            list.clear();
        }

        dbHelper = new InventoryDbHelper(context);

        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String[] projection = {
                InventoryContract.InventoryEntry._ID,
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_PRICE,
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY,
                InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME,
                InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE_NUMBER
        };

        Cursor cursor = database.query(
                InventoryContract.InventoryEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int idColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE_NUMBER);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentPrice = cursor.getString(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);
                list.add(new Product(currentId, currentName, currentPrice, currentQuantity, currentSupplierName, currentSupplierPhone));
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public static void insertData(String productName, String productPrice, int quantity, String supplierName, String supplierPhoneNumber) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME, productName);
        contentValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_PRICE, productPrice);
        contentValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        contentValues.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME, supplierName);
        contentValues.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE_NUMBER, supplierPhoneNumber);
        database.insert(InventoryContract.InventoryEntry.TABLE_NAME, null, contentValues);
    }

    public static void deleteData(long id) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(InventoryContract.InventoryEntry.TABLE_NAME, InventoryContract.InventoryEntry._ID + " = " + id, null);
    }

    public static void updateData(long id, String productName, String productPrice, int quantity, String supplierName, String supplierPhoneNumber) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME, productName);
        contentValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_PRICE, productPrice);
        contentValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        contentValues.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME, supplierName);
        contentValues.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE_NUMBER, supplierPhoneNumber);
        database.update(InventoryContract.InventoryEntry.TABLE_NAME, contentValues, InventoryContract.InventoryEntry._ID + " = " + id, null);
    }
}
