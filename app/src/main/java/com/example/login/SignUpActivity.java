package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login.entities.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class SignUpActivity extends AppCompatActivity {

    private EditText ed_name;
    private EditText ed_password;
    private EditText ed_email;
    private TextView tv_registered;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ed_name = findViewById(R.id.ed_name);
        ed_password = findViewById(R.id.ed_password);
        ed_email = findViewById(R.id.ed_email);
        Button btn_register2 = findViewById(R.id.btn_register2);
        tv_registered = findViewById(R.id.tv_registered);

        db = FirebaseFirestore.getInstance();

        btn_register2.setOnClickListener(v -> signUp());
    }

    private void signUp() {
        String name = ed_name.getText().toString().trim();
        String email = ed_email.getText().toString().trim();
        String password = ed_password.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            tv_registered.setText("All fields must be filled!");
            return;
        }

        // Validar si el nombre o email ya están registrados
        db.collection("users")
                .whereEqualTo("name", name)
                .get()
                .addOnSuccessListener(nameSnapshot -> {
                    if (!nameSnapshot.isEmpty()) {
                        tv_registered.setText("Name is already in use!");
                    } else {
                        db.collection("users")
                                .whereEqualTo("email", email)
                                .get()
                                .addOnSuccessListener(emailSnapshot -> {
                                    if (!emailSnapshot.isEmpty()) {
                                        tv_registered.setText("Email is already in use!");
                                    } else {
                                        // Registrar nuevo usuario
                                        User user = new User(name, email, password);
                                        db.collection("users").add(user)
                                                .addOnSuccessListener(documentReference -> {
                                                    tv_registered.setText("User registered successfully!");
                                                    Intent intent = new Intent(this, LoginActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                })
                                                .addOnFailureListener(e -> tv_registered.setText("Registration failed."));
                                    }
                                });
                    }
                });
    }
}
