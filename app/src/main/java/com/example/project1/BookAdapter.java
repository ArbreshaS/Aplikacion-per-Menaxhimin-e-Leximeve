package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context context;
    private List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.titleTextView.setText(book.getTitle());
        holder.authorTextView.setText(book.getAuthor());
        holder.descriptionTextView.setText(book.getDescription());
        holder.yearTextView.setText(String.valueOf(book.getYear()));
        holder.totalPagesTextView.setText(String.valueOf(book.getTotalPages()));
        holder.ratingTextView.setText(String.valueOf(book.getRating()));

        // Edit button
        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), EditBookActivity.class);
            intent.putExtra("documentId", book.getDocumentId());
            intent.putExtra("title", book.getTitle());
            intent.putExtra("author", book.getAuthor());
            intent.putExtra("description", book.getDescription());
            intent.putExtra("year", book.getYear());
            intent.putExtra("totalPages", book.getTotalPages());
            intent.putExtra("rating", book.getRating());
            holder.itemView.getContext().startActivity(intent);
        });

        // Delete button
        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Book")
                    .setMessage("Are you sure you want to delete this book?")
                    .setPositiveButton("Yes", (dialog, which) -> deleteBook(book, position))
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    private void deleteBook(Book book, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("books").document(book.getDocumentId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    bookList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Book deleted successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(context, "Failed to delete book.", Toast.LENGTH_SHORT).show());
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, authorTextView, descriptionTextView, yearTextView, totalPagesTextView, ratingTextView;
        Button editButton, deleteButton;

        public BookViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.book_title);
            authorTextView = itemView.findViewById(R.id.book_author);
            descriptionTextView = itemView.findViewById(R.id.book_description);
            yearTextView = itemView.findViewById(R.id.book_year);
            totalPagesTextView = itemView.findViewById(R.id.book_totalPages);
            ratingTextView = itemView.findViewById(R.id.book_rating);
            editButton = itemView.findViewById(R.id.btn_edit);
            deleteButton = itemView.findViewById(R.id.btn_delete);
        }
    }
}