package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private TextView emptyMessage;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        emptyMessage = findViewById(R.id.emptyMessage);
        recyclerView = findViewById(R.id.recyclerViewBooks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(this, bookList); // Pass 'this' as context
        recyclerView.setAdapter(bookAdapter);


        db = FirebaseFirestore.getInstance();
        fetchBooks();

        Button btnAddBook = findViewById(R.id.btnAddBook);
        btnAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, AddBookActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchBooks();
    }

    private void fetchBooks() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("books")
                .whereEqualTo("userId", currentUserId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        bookList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String title = document.getString("Title");
                            String author = document.getString("Author");
                            String description = document.getString("Description");
                            int year = document.contains("Year") ? document.getLong("Year").intValue() : 0;
                            int totalPages = document.contains("TotalPages") ? document.getLong("TotalPages").intValue() : 0;
                            boolean completed = document.contains("Completed") ? document.getBoolean("Completed") : false;
                            int rating = document.contains("Rating") ? document.getLong("Rating").intValue() : 0;

                            Book book = new Book(title, author, description, year, 0, totalPages, rating);
                            book.setDocumentId(document.getId());

                            book.setDocumentId(document.getId());
                            bookList.add(book);
                        }

                        bookAdapter.notifyDataSetChanged();
                        if (bookList.isEmpty()) {
                            emptyMessage.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            emptyMessage.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(Home.this, "Failed to load books.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Home.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
