package com.example.project1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Enable the back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Get the current user from FirebaseAuth
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Display the user's email in a TextView
        TextView emailTextView = findViewById(R.id.user_email); // Ensure you have this TextView in your layout
        if (user != null && user.getEmail() != null) {
            emailTextView.setText(user.getEmail());
        } else {
            emailTextView.setText("No user is currently signed in.");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle back button click here
        finish(); // Closes the current activity and goes back
        return true;
    }
}
