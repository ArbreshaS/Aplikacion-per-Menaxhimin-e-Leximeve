package com.example.project1;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> completedBooks;
    private FirebaseFirestore db;
    private TextView userEmailTextView; // TextView for displaying user email

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Firestore and FirebaseAuth
        db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        // Initialize views
        recyclerView = findViewById(R.id.recyclerViewCompletedBooks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userEmailTextView = findViewById(R.id.textViewUserEmail); // Reference to email TextView

        // Display user email
        if (auth.getCurrentUser() != null) {
            String userEmail = auth.getCurrentUser().getEmail();
            userEmailTextView.setText("Email: " + userEmail); // Set email in the TextView
        } else {
            userEmailTextView.setText("Email: Not available");
        }

        // Initialize list and adapter
        completedBooks = new ArrayList<>();
        bookAdapter = new BookAdapter(this, completedBooks, true); // Set 'true' for Profile View
        recyclerView.setAdapter(bookAdapter);

        // Load completed books
        loadCompletedBooks();
    }

    private void loadCompletedBooks() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("books")
                .whereEqualTo("userId", currentUserId)
                .whereEqualTo("completed", true)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        completedBooks.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String title = document.getString("Title");
                            String author = document.getString("Author");
                            String description = document.getString("Description");
                            int year = document.contains("Year") ? document.getLong("Year").intValue() : 0;
                            int totalPages = document.contains("TotalPages") ? document.getLong("TotalPages").intValue() : 0;
                            int currentPage = document.contains("CurrentPage") ? document.getLong("CurrentPage").intValue() : 0;
                            float rating = document.contains("Rating") ? document.getDouble("Rating").floatValue() : 0.0f;

                            Book book = new Book(title, author, description, year, currentPage, totalPages);
                            book.setRating(rating);
                            book.setCompleted(true); // Mark the book as completed for adapter logic
                            completedBooks.add(book);
                        }
                        bookAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Profile.this, "Failed to load completed books.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Profile.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
