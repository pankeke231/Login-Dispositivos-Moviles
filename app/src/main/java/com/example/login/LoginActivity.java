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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    private EditText it_name;
    private EditText it_password;
    private Button btn_login;
    private TextView tv_logeado;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        it_name = findViewById(R.id.it_name);
        it_password = findViewById(R.id.it_password);
        btn_login = findViewById(R.id.btn_login);
        tv_logeado = findViewById(R.id.tv_logeado);

        db = FirebaseFirestore.getInstance();

        btn_login.setOnClickListener(v -> openPageApp());
    }

    private void openPageApp() {
        String name = it_name.getText().toString().trim();
        String password = it_password.getText().toString().trim();

        if (name.isEmpty() || password.isEmpty()) {
            tv_logeado.setText("Fields cannot be empty!");
            return;
        }

        db.collection("users")
                .whereEqualTo("name", name)
                .whereEqualTo("password", password)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (!snapshot.isEmpty()) {
                        Intent intent = new Intent(this, PageActivity.class);
                        intent.putExtra("username", name); // 👈 AQUI agregamos el nombre
                        startActivity(intent);
                        finish();
                    } else {
                        tv_logeado.setText("Incorrect username or password!");
                    }
                })
                .addOnFailureListener(e -> {
                    tv_logeado.setText("Login failed. Please try again.");
                });
    }

}
