package com.example.project1;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddBookActivity extends AppCompatActivity {

    private EditText titleEditText, authorEditText, descriptionEditText, yearEditText, totalPagesEditText, currentPageEditText;
    private Button saveButton;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);


        titleEditText = findViewById(R.id.editTextTitle);
        authorEditText = findViewById(R.id.editTextAuthor);
        descriptionEditText = findViewById(R.id.editTextDescription);
        yearEditText = findViewById(R.id.editTextYear);
        totalPagesEditText = findViewById(R.id.editTextTotalPages);
        currentPageEditText = findViewById(R.id.editTextCurrentPage);
        saveButton = findViewById(R.id.btnSaveBook);


        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        saveButton.setOnClickListener(v -> saveBook());
    }

    private void saveBook() {

        String title = titleEditText.getText().toString().trim();
        String author = authorEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String yearText = yearEditText.getText().toString().trim();
        String totalPagesText = totalPagesEditText.getText().toString().trim();
        String currentPageText = currentPageEditText.getText().toString().trim();


        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(description) ||
                TextUtils.isEmpty(yearText) || TextUtils.isEmpty(totalPagesText) || TextUtils.isEmpty(currentPageText)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        int year, totalPages, currentPage;
        try {
            year = Integer.parseInt(yearText);
            totalPages = Integer.parseInt(totalPagesText);
            currentPage = Integer.parseInt(currentPageText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers for year, total pages, and current page", Toast.LENGTH_SHORT).show();
            return;
        }


        String userId = auth.getCurrentUser().getUid();

        Book newBook = new Book(title, author, description, year, currentPage, totalPages);
        newBook.setCompleted(false);


        db.collection("books")
                .add(newBook.toMap(userId))
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(AddBookActivity.this, "Book added successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddBookActivity.this, "Error adding book", Toast.LENGTH_SHORT).show();
                });
    }
}
