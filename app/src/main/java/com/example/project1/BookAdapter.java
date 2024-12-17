package com.example.project1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        // Inflate the layout for each book item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        // Get the current book from the list
        Book book = bookList.get(position);

        // Set the data for each TextView
        holder.titleTextView.setText(book.getTitle());
        holder.authorTextView.setText(book.getAuthor());
        holder.descriptionTextView.setText(book.getDescription());
        holder.yearTextView.setText(String.valueOf(book.getYear()));
        holder.currentPageTextView.setText(String.valueOf(book.getCurrentPage()));
        holder.totalPagesTextView.setText(String.valueOf(book.getTotalPages()));
        holder.ratingTextView.setText(String.valueOf(book.getRating()));

        // Set the Completed status text
        if (book.isCompleted()) {
            holder.completedTextView.setText("Completed");
        } else {
            holder.completedTextView.setText("In Progress");
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    // ViewHolder class to hold references to each item view
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView authorTextView;
        TextView descriptionTextView;
        TextView yearTextView;
        TextView currentPageTextView;
        TextView totalPagesTextView;
        TextView ratingTextView;
        TextView completedTextView;

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
        }
    }
}
