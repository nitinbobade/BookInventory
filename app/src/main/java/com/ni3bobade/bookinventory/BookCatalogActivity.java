package com.ni3bobade.bookinventory;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ni3bobade.bookinventory.data.BookContract.BookEntry;
import com.ni3bobade.bookinventory.data.BookDbHelper;

import java.text.MessageFormat;

public class BookCatalogActivity extends AppCompatActivity {

    private BookDbHelper bookDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openEditorActivityIntent = new Intent(BookCatalogActivity.this, EditorActivity.class);
                startActivity(openEditorActivityIntent);
            }
        });

        // To access database, instantiate subclass of SQLiteOpenHelper
        bookDbHelper = new BookDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    // Temporary helper method to display info about books database
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = bookDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database you will actually use after this query
        String[] projection = {BookEntry._ID, BookEntry.COLUMN_BOOK_NAME, BookEntry.COLUMN_BOOK_PRICE, BookEntry.COLUMN_BOOK_QUANTITY, BookEntry.COLUMN_BOOK_SUPPLIER_NAME, BookEntry.COLUMN_BOOK_SUPPLIER_CONTACT};

        // Perform a query on books table
        Cursor cursor = db.query(BookEntry.TABLE_NAME, projection, null, null, null, null, null, null);

        TextView displayBookCatalogTextView = findViewById(R.id.displayBookCatalogTextView);

        try {
            displayBookCatalogTextView.setText(MessageFormat.format("{0} {1} {2}\n\n", getString(R.string.book_inventory_contains), cursor.getCount(), getString(R.string._books)));
            displayBookCatalogTextView.append(BookEntry._ID + " | " + BookEntry.COLUMN_BOOK_NAME + " | " + BookEntry.COLUMN_BOOK_PRICE + " | " + BookEntry.COLUMN_BOOK_QUANTITY + " | " + BookEntry.COLUMN_BOOK_SUPPLIER_NAME + " | " + BookEntry.COLUMN_BOOK_SUPPLIER_CONTACT + "\n");

            // figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int bookNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
            int bookPriceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
            int bookQuantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);
            int bookSupplierNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPPLIER_NAME);
            int bookSupplierPhoneNumberColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPPLIER_CONTACT);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentBookName = cursor.getString(bookNameColumnIndex);
                int currentBookPrice = cursor.getInt(bookPriceColumnIndex);
                int currentBookQuantity = cursor.getInt(bookQuantityColumnIndex);
                String currentBookSupplierName = cursor.getString(bookSupplierNameColumnIndex);
                String currentBookSupplierPhoneNumber = cursor.getString(bookSupplierPhoneNumberColumnIndex);

                // Display the values from each column of current row
                displayBookCatalogTextView.append("\n" + currentID + " | " + currentBookName + " | " + currentBookPrice + " | " + currentBookQuantity + " | " + currentBookSupplierName + " | " + currentBookSupplierPhoneNumber + "\n");
            }

        } finally {
            // Always close cursor to release all it's resources
            cursor.close();
        }
    }
}
