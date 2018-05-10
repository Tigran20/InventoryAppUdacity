package com.alextroy.inventory.model;

import android.provider.BaseColumns;

public class InventoryContract {

    private InventoryContract() {
    }

    public static final class InventoryEntry implements BaseColumns {

        public static final String TABLE_NAME = "inventory";

        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";

        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";
    }
}
