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

public class LoginActivity extends AppCompatActivity {

    private EditText it_name;
    private EditText it_password;
    private Button btn_login;
    private TextView tv_logeado;

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

        btn_login.setOnClickListener(v -> openPageApp());
    }

    private void openPageApp() {
        String name = it_name.getText().toString().trim();
        String password = it_password.getText().toString().trim();

        if (name.isEmpty() || password.isEmpty()) {
            tv_logeado.setText("Fields cannot be empty!");
            return;
        }

        // Aquí puedes agregar la lógica para autenticar al usuario mediante tu API
        // Por ejemplo, haciendo una petición HTTP con Retrofit o cualquier otra forma.
        // Simulación (temporal):
        if (name.equals("admin") && password.equals("1234")) {
            Intent intent = new Intent(this, PageActivity.class);
            intent.putExtra("username", name);
            startActivity(intent);
            finish();
        } else {
            tv_logeado.setText("Incorrect username or password!");
        }
    }
}
