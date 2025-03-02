package com.example.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

    }

    public String signUp(String ed_name, String ed_email, String ed_password){

        return "s";
    }

}