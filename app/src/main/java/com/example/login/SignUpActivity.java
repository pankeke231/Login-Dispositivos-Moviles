package com.example.login;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private EditText ed_name;
    private EditText ed_password;
    private EditText ed_email;
    private Button btn_register2;
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
        btn_register2 = findViewById(R.id.btn_register2);
        tv_registered = findViewById(R.id.tv_registered);

        Button btn_register2 = findViewById(R.id.btn_register2);
        btn_register2.setOnClickListener(v -> signUp());

    }

    // Validar el nombre que se recibe, si ya está en los usuarios creados anteriormente,
    // no se puede registrar.
    public boolean nameValid() {

        String name = ed_name.getText().toString().trim();

        if (name.isEmpty()) {
            tv_registered.setText("Name field is empty!");
            return false;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("users_prefs", MODE_PRIVATE);
        if (sharedPreferences.contains(name)) {
            tv_registered.setText("Name is already in use!");
            return false;
        }

        return true;
    }

    // Validar el email que se recibe, si ya está en los usuarios creados anteriormente,
    // no se puede registrar.
    public boolean emailValid() {

        String email = ed_email.getText().toString().trim();

        if (email.isEmpty()) {
            tv_registered.setText("Email field is empty!");
            return false;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("users_prefs", MODE_PRIVATE);
        if (sharedPreferences.contains(email)) {
            tv_registered.setText("Email is already in use!");
            return false;
        }

        return true;
    }

    private void saveUser(String name, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("users_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, password); // Guarda usuario y contraseña
        editor.apply();
    }

    public void signUp() {

        if (nameValid() && emailValid()) {
            String name = ed_name.getText().toString().trim();
            String password = ed_password.getText().toString().trim();
//            String email = ed_email.getText().toString().trim();

            if (password.isEmpty()) {
                tv_registered.setText("Password field is empty!");
                return;
            }

            saveUser(name, password); // Guarda el usuario

            tv_registered.setText("User registered successfully!");

            // Ir a LoginActivity después de registrar
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            tv_registered.setText("User not created!");
        }

    }

}