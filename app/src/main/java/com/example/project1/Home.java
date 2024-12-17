package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    private TextView emptyMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Enable the back button (Up button) in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize the empty message TextView
        emptyMessage = findViewById(R.id.emptyMessage);

        // Button to add a book
        Button btnAddBook = findViewById(R.id.btnAddBook);
        btnAddBook.setOnClickListener(v -> {
            // Open the AddBookActivity
            Intent intent = new Intent(Home.this, AddBookActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}