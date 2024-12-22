package com.example.project1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Lidhja e elementeve të layout-it me Java
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnSignUp = findViewById(R.id.btn_signup);

        // Vendosja e një listener për butonin "Sign Up"
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignUp();
            }
        });
    }

    private void handleSignUp() {
        // Merrni të dhënat nga fushat e hyrjes
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Verifikoni nëse të dhënat janë të plotësuara
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Ju lutemi plotësoni të gjitha fushat", Toast.LENGTH_SHORT).show();
            return;
        }

        // Logjika e regjistrimit (mund ta zëvendësosh me logjikën e bazës së të dhënave ose API-të)
        Toast.makeText(SignUpActivity.this, "Regjistrimi u krye me sukses për: " + name, Toast.LENGTH_SHORT).show();

        // Mund të shtosh logjikën për t'u rikthyer në një aktivitet tjetër
        // finish();
    }
}
