package com.example.project1;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditBookActivity extends AppCompatActivity {

    private EditText titleEditText, authorEditText, descriptionEditText, yearEditText, totalPagesEditText, ratingEditText;
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
        ratingEditText = findViewById(R.id.editTextRating);
        saveButton = findViewById(R.id.btnSaveBook);

        db = FirebaseFirestore.getInstance();

        // Retrieve data from intent
        documentId = getIntent().getStringExtra("documentId");
        titleEditText.setText(getIntent().getStringExtra("title"));
        authorEditText.setText(getIntent().getStringExtra("author"));
        descriptionEditText.setText(getIntent().getStringExtra("description"));
        yearEditText.setText(String.valueOf(getIntent().getIntExtra("year", 0)));
        totalPagesEditText.setText(String.valueOf(getIntent().getIntExtra("totalPages", 0)));
        ratingEditText.setText(String.valueOf(getIntent().getIntExtra("rating", 0)));

        // Save button listener
        saveButton.setOnClickListener(v -> updateBook());
    }

    private void updateBook() {
        String title = titleEditText.getText().toString().trim();
        String author = authorEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String yearText = yearEditText.getText().toString().trim();
        String totalPagesText = totalPagesEditText.getText().toString().trim();
        String ratingText = ratingEditText.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(description) ||
                TextUtils.isEmpty(yearText) || TextUtils.isEmpty(totalPagesText) || TextUtils.isEmpty(ratingText)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> bookData = new HashMap<>();
        bookData.put("Title", title);
        bookData.put("Author", author);
        bookData.put("Description", description);
        bookData.put("Year", Integer.parseInt(yearText));
        bookData.put("TotalPages", Integer.parseInt(totalPagesText));
        bookData.put("Rating", Integer.parseInt(ratingText));

        db.collection("books").document(documentId)
                .update(bookData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditBookActivity.this, "Book updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(EditBookActivity.this, "Failed to update book", Toast.LENGTH_SHORT).show());
    }
}
