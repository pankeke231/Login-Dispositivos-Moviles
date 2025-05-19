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

public class SignUpActivity extends AppCompatActivity {

    private EditText ed_name;
    private EditText ed_password;
    private EditText ed_email;
    private TextView tv_registered;

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

        // Aquí iría la lógica de validación/registro local (si la implementas)
        // Por ahora solo muestra éxito e inicia LoginActivity

        tv_registered.setText("User registered successfully!");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
