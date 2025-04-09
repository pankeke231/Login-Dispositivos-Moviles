package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.entities.Producto;

import java.util.ArrayList;
import java.util.List;

public class PageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Producto> productList;
    private Button logoutButton;
    private TextView tvUsername; // TextView para mostrar el nombre del usuario
    private String username;     // <- Declaración única aquí

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        // Inicializar TextView
        tvUsername = findViewById(R.id.tv_username);
        username = getIntent().getStringExtra("username");  // Asignación sin volver a declarar
        if (username != null) {
            tvUsername.setText("Bienvenid@, " + username);
        }

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lista de productos
        productList = new ArrayList<>();
        productList.add(new Producto("Tarjeta RP", "Tarjeta de Riot Points", R.drawable.rp, 100));
        productList.add(new Producto("Camiseta LoL", "Camiseta oficial de League of Legends", R.drawable.rp, 200));
        productList.add(new Producto("Figura Yasuo", "Figura coleccionable de Yasuo", R.drawable.rp, 300));

        // Adaptador con username
        productAdapter = new ProductAdapter(productList, username);
        recyclerView.setAdapter(productAdapter);

        // Botón de cerrar sesión
        logoutButton = findViewById(R.id.btn_logout);
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(PageActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
