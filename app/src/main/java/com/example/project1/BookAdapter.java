package com.example.project1;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> bookList;

    public BookAdapter(List<Book> bookList) {
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
        holder.currentPageTextView.setText(String.valueOf(book.getCurrentPage()));
        holder.totalPagesTextView.setText(String.valueOf(book.getTotalPages()));
        holder.ratingTextView.setText(String.valueOf(book.getRating()));

        if (book.isCompleted()) {
            holder.completedTextView.setText("Completed");
        } else {
            holder.completedTextView.setText("In Progress");
        }

        // Set up Edit button
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
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView authorTextView;
        TextView descriptionTextView;
        TextView yearTextView;
        TextView currentPageTextView;
        TextView totalPagesTextView;
        TextView ratingTextView;
        TextView completedTextView;
        Button editButton; // Add this

        public BookViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.book_title);
            authorTextView = itemView.findViewById(R.id.book_author);
            descriptionTextView = itemView.findViewById(R.id.book_description);
            yearTextView = itemView.findViewById(R.id.book_year);
            currentPageTextView = itemView.findViewById(R.id.book_currentPage);
            totalPagesTextView = itemView.findViewById(R.id.book_totalPages);
            ratingTextView = itemView.findViewById(R.id.book_rating);
            completedTextView = itemView.findViewById(R.id.book_completed);
            editButton = itemView.findViewById(R.id.btn_edit); // Add this
        }
    }
}
