package com.example.project1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class EditBookActivity extends AppCompatActivity {

    private EditText titleEditText, authorEditText, descriptionEditText, yearEditText, totalPagesEditText, currentPageEditText;
    private Button saveButton;
    private FirebaseFirestore db;
    private String documentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        titleEditText = findViewById(R.id.editTextTitle);
        authorEditText = findViewById(R.id.editTextAuthor);
        descriptionEditText = findViewById(R.id.editTextDescription);
        yearEditText = findViewById(R.id.editTextYear);
        totalPagesEditText = findViewById(R.id.editTextTotalPages);
        currentPageEditText = findViewById(R.id.editTextCurrentPage);
        saveButton = findViewById(R.id.btnSaveBook);

        db = FirebaseFirestore.getInstance();

        // Get intent data
        documentId = getIntent().getStringExtra("documentId");
        titleEditText.setText(getIntent().getStringExtra("title"));
        authorEditText.setText(getIntent().getStringExtra("author"));
        descriptionEditText.setText(getIntent().getStringExtra("description"));
        yearEditText.setText(String.valueOf(getIntent().getIntExtra("year", 0)));
        totalPagesEditText.setText(String.valueOf(getIntent().getIntExtra("totalPages", 0)));
        currentPageEditText.setText(String.valueOf(getIntent().getIntExtra("currentPage", 0)));

        saveButton.setOnClickListener(v -> updateBook());
    }

    private void updateBook() {
        String title = titleEditText.getText().toString();
        String author = authorEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        int year = Integer.parseInt(yearEditText.getText().toString());
        int totalPages = Integer.parseInt(totalPagesEditText.getText().toString());
        int currentPage = Integer.parseInt(currentPageEditText.getText().toString());

        db.collection("books").document(documentId)
                .update("Title", title,
                        "Author", author,
                        "Description", description,
                        "Year", year,
                        "TotalPages", totalPages,
                        "CurrentPage", currentPage)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditBookActivity.this, "Book updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditBookActivity.this, "Error updating book: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
