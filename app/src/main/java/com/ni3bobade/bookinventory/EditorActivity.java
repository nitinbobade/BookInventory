package com.ni3bobade.bookinventory;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ni3bobade.bookinventory.data.BookContract.BookEntry;
import com.ni3bobade.bookinventory.data.BookDbHelper;

public class EditorActivity extends AppCompatActivity {

    private TextInputLayout bookNameTextInput;
    private TextInputLayout bookPriceTextInput;
    private TextInputLayout bookQuantityTextInput;
    private TextInputLayout bookSupplierNameTextInput;
    private TextInputLayout bookSupplierContactTextInput;

    private EditText bookNameEditText;
    private EditText bookPriceEditText;
    private EditText bookQuantityEditText;
    private EditText bookSupplierNameEditText;
    private EditText bookSupplierContactEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        bookNameTextInput = findViewById(R.id.bookNameTextInput);
        bookPriceTextInput = findViewById(R.id.bookPriceTextInput);
        bookQuantityTextInput = findViewById(R.id.bookQuantityTextInput);
        bookSupplierNameTextInput = findViewById(R.id.bookSupplierNameTextInput);
        bookSupplierContactTextInput = findViewById(R.id.bookSupplierContactTextInput);

        bookNameEditText = findViewById(R.id.bookNameEditText);
        bookPriceEditText = findViewById(R.id.bookPriceEditText);
        bookQuantityEditText = findViewById(R.id.bookQuantityEditText);
        bookSupplierNameEditText = findViewById(R.id.bookSupplierNameEditText);
        bookSupplierContactEditText = findViewById(R.id.bookSupplierContactEditText);

        Button addBookToInventoryButton = findViewById(R.id.addBookToInventoryButton);
        addBookToInventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBookToInventory();
            }
        });
    }

    // Get user input from editor and save new book into database
    private void addBookToInventory() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String bookNameString;
        if (TextUtils.isEmpty(bookNameEditText.getText())) {
            bookNameTextInput.setError(getString(R.string.book_name_required));
            return;
        } else {
            bookNameTextInput.setError(null);   // Clear the error
            bookNameString = bookNameEditText.getText().toString().trim();
        }

        String bookPriceString;
        if (TextUtils.isEmpty(bookPriceEditText.getText())) {
            bookPriceTextInput.setError(getString(R.string.book_price_required));
            return;
        } else {
            bookPriceTextInput.setError(null);   // Clear the error
            bookPriceString = bookPriceEditText.getText().toString().trim();
        }

        int bookPrice = Integer.parseInt(bookPriceString);
        if (bookPrice < 0) {
            Toast.makeText(this, getString(R.string.book_price_can_not_be_less), Toast.LENGTH_SHORT).show();
        }

        String bookQuantityString;
        if (TextUtils.isEmpty(bookQuantityEditText.getText())) {
            bookQuantityTextInput.setError(getString(R.string.book_quantity_required));
            return;
        } else {
            bookQuantityTextInput.setError(null);   // Clear the error
            bookQuantityString = bookQuantityEditText.getText().toString().trim();
        }

        int bookQuantity = Integer.parseInt(bookQuantityString);
        if (bookQuantity < 0) {
            Toast.makeText(this, getString(R.string.book_quantity_can_not_be_less), Toast.LENGTH_SHORT).show();
        }

        String bookSupplierNameString;
        if (TextUtils.isEmpty(bookSupplierNameEditText.getText())) {
            bookSupplierNameTextInput.setError(getString(R.string.book_supplier_name_required));
            return;
        } else {
            bookSupplierNameTextInput.setError(null);   // Clear the error
            bookSupplierNameString = bookSupplierNameEditText.getText().toString().trim();
        }

        String bookSupplierContactString;
        if (TextUtils.isEmpty(bookSupplierContactEditText.getText())) {
            bookSupplierContactTextInput.setError(getString(R.string.book_supplier_contact_required));
            return;
        } else {
            bookSupplierContactTextInput.setError(null);   // Clear the error
            bookSupplierContactString = bookSupplierContactEditText.getText().toString().trim();
        }

        // Create database helper
        BookDbHelper bookDbHelper = new BookDbHelper(this);

        // Get the database in write mode
        SQLiteDatabase db = bookDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are keys,
        // and book attributes from editor are the values
        ContentValues contentValues = new ContentValues();
        contentValues.put(BookEntry.COLUMN_BOOK_NAME, bookNameString);
        contentValues.put(BookEntry.COLUMN_BOOK_PRICE, bookPrice);
        contentValues.put(BookEntry.COLUMN_BOOK_QUANTITY, bookQuantity);
        contentValues.put(BookEntry.COLUMN_BOOK_SUPPLIER_NAME, bookSupplierNameString);
        contentValues.put(BookEntry.COLUMN_BOOK_SUPPLIER_CONTACT, bookSupplierContactString);

        // Insert a new row for book in database, returning ID of that new row
        long newRowId = db.insert(BookEntry.TABLE_NAME, null, contentValues);

        // Show a Toast message depending upon whether or not book insertion was successful
        if (newRowId == -1) {
            // If row ID is -1, then there was an error with book insertion
            Toast.makeText(this, getString(R.string.error_saving_book), Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise book insertion was successful and we can display a Toast message with row ID
            Toast.makeText(this, getString(R.string.book_saved) + newRowId, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
