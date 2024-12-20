package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context context;
    private List<Book> bookList;
    private boolean isProfileView;

    public BookAdapter(Context context, List<Book> bookList, boolean isProfileView) {
        this.context = context;
        this.bookList = bookList;
        this.isProfileView = isProfileView;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.titleTextView.setText("Book: " + book.getTitle());
        holder.authorTextView.setText("By: " + book.getAuthor());
        holder.descriptionTextView.setText("Description: " + book.getDescription());
        holder.yearTextView.setText("Year: " + book.getYear());
        holder.totalPagesTextView.setText("Total Pages: " + book.getTotalPages());
        holder.currentPageTextView.setText("Current Page: " + book.getCurrentPage());

        if (book.isCompleted()) {
            holder.ratingBar.setVisibility(View.VISIBLE);
            holder.ratingBar.setRating(book.getRating());

            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
            holder.completedButton.setVisibility(View.GONE);
        } else {
            if (isProfileView) {
                holder.editButton.setVisibility(View.GONE);
                holder.deleteButton.setVisibility(View.GONE);
                holder.completedButton.setVisibility(View.GONE);
                holder.ratingBar.setVisibility(View.GONE);
            } else {
                holder.editButton.setVisibility(View.VISIBLE);
                holder.deleteButton.setVisibility(View.VISIBLE);
                holder.completedButton.setVisibility(View.VISIBLE);
                holder.ratingBar.setVisibility(View.GONE);
            }
        }

        holder.completedButton.setOnClickListener(v -> openRatingDialog(book, position));

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditBookActivity.class);
            intent.putExtra("documentId", book.getDocumentId());
            intent.putExtra("title", book.getTitle());
            intent.putExtra("author", book.getAuthor());
            intent.putExtra("description", book.getDescription());
            intent.putExtra("year", book.getYear());
            intent.putExtra("totalPages", book.getTotalPages());
            intent.putExtra("currentPage", book.getCurrentPage());
            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Book")
                    .setMessage("Are you sure you want to delete this book?")
                    .setPositiveButton("Yes", (dialog, which) -> deleteBook(book, position))
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    private void openRatingDialog(Book book, int position) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_rate_book, null);
        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);

        new AlertDialog.Builder(context)
                .setTitle("Rate Book")
                .setView(dialogView)
                .setPositiveButton("Submit", (dialog, which) -> {
                    float rating = ratingBar.getRating();
                    if (rating > 0) {
                        saveRatingAndComplete(book, rating, position);
                    } else {
                        Toast.makeText(context, "Please provide a rating.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void saveRatingAndComplete(Book book, float rating, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        book.setCompleted(true);
        book.setRating(rating);

        db.collection("books").document(book.getDocumentId())
                .update("completed", true, "Rating", rating)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Book marked as completed!", Toast.LENGTH_SHORT).show();
                    bookList.remove(position);
                    notifyItemRemoved(position);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to mark as completed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void deleteBook(Book book, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("books").document(book.getDocumentId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Book deleted successfully!", Toast.LENGTH_SHORT).show();
                    bookList.remove(position);
                    notifyItemRemoved(position);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to delete book: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, authorTextView, descriptionTextView, yearTextView, totalPagesTextView, currentPageTextView;
        RatingBar ratingBar;
        Button completedButton, editButton, deleteButton;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.book_title);
            authorTextView = itemView.findViewById(R.id.book_author);
            descriptionTextView = itemView.findViewById(R.id.book_description);
            yearTextView = itemView.findViewById(R.id.book_year);
            totalPagesTextView = itemView.findViewById(R.id.book_totalPages);
            currentPageTextView = itemView.findViewById(R.id.book_currentPage);
            ratingBar = itemView.findViewById(R.id.book_ratingBar);
            completedButton = itemView.findViewById(R.id.btn_completed);
            editButton = itemView.findViewById(R.id.btn_edit);
            deleteButton = itemView.findViewById(R.id.btn_delete);
        }
    }
}
