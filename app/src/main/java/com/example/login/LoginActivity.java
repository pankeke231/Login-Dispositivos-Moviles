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

import java.util.List;

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

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(v -> openPageApp());
    }

//    private User getUserByName(String name) {
//        SharedPreferences sharedPreferences = getSharedPreferences("users_prefs", MODE_PRIVATE);
//        String userData = sharedPreferences.getString(name, null);
//
//        if (userData != null) {
//            String[] parts = userData.split(",");
//            return new User(parts[0], parts[1], parts[2]); // name, email, password
//        }
//        return null;
//    }

//    public boolean isTruePassword() {
//        String name = it_name.getText().toString().trim();
//        String password = it_password.getText().toString().trim();
//
//        User user = getUserByName(name);
//        return user != null && user.getPassword().equals(password);
//    }

    //    public boolean isTrueUserName (){
//        String it_name = this.it_name.getText().toString();
//        for (User user : users){
//            if (user.getName().equals(it_name)){
//                return true;
//            }
//        }
//        return false;
//    }
    // Verifica si el usuario y la contraseña son correctos
    public boolean isValidUser() {
        String name = it_name.getText().toString().trim();
        String password = it_password.getText().toString().trim();

        if (name.isEmpty() || password.isEmpty()) {
            tv_logeado.setText("Fields cannot be empty!");
            return false;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("users_prefs", MODE_PRIVATE);
        String savedPassword = sharedPreferences.getString(name, null);

        if (savedPassword != null && savedPassword.equals(password)) {
            return true;
        } else {
            tv_logeado.setText("Incorrect username or password!");
            return false;
        }
    }
//    public boolean isRegisteredUser(String name) {
//        SharedPreferences sharedPreferences = getSharedPreferences("users_prefs", MODE_PRIVATE);
//        return sharedPreferences.contains(name);
//    }

//    public boolean login() {
//        return isTruePassword() && isRegisteredUser(it_name.getText().toString());
//    }

    //Para abrir la activity despues de logearse
    public void openPageApp() {
        if (isValidUser()) {
            Intent intent = new Intent(this, PageActivity.class);
            startActivity(intent);
            finish(); // Cierra la pantalla de login
        }
    }


}