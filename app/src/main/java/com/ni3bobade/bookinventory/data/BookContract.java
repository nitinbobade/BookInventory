package com.ni3bobade.bookinventory.data;

import android.provider.BaseColumns;

public final class BookContract {

    // To prevent someone from accidentally instantiating contract class, give it an empty constructor
    private BookContract() {

    }

    // Inner class representing constant values for books database table
    // Each entry in the table represents single book
    public static final class BookEntry implements BaseColumns {

        // Name of the database table
        public static final String TABLE_NAME = "books";

        // Unique ID number for the book
        // Type: INTEGER
        public static final String _ID = BaseColumns._ID;

        // Name of the book
        // Type: TEXT
        public static final String COLUMN_BOOK_NAME = "bookName";

        // Price of the book
        // Type: INTEGER
        public static final String COLUMN_BOOK_PRICE = "bookPrice";

        // Quantity of the book
        // Type: INTEGER
        public static final String COLUMN_BOOK_QUANTITY = "bookQuantity";

        // Name of the book supplier
        // Type: TEXT
        public static final String COLUMN_BOOK_SUPPLIER_NAME = "bookSupplierName";

        // Book Supplier phone no.
        // Type: TEXT
        public static final String COLUMN_BOOK_SUPPLIER_CONTACT = "bookSupplierPhoneNumber";
    }
}
