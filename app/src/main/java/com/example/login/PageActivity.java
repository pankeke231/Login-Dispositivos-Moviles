package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.entities.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class PageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lista de productos
        productList = new ArrayList<>();
        productList.add(new Product("Tarjeta RP", "Tarjeta de Riot Points", R.drawable.rp));
        productList.add(new Product("Camiseta LoL", "Camiseta oficial de League of Legends", R.drawable.rp));
        productList.add(new Product("Figura Yasuo", "Figura coleccionable de Yasuo", R.drawable.rp));

        // Adaptador
        productAdapter = new ProductAdapter(productList);
        recyclerView.setAdapter(productAdapter);



        // Botón de cerrar sesión
        logoutButton = findViewById(R.id.btn_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Cierra esta actividad para que no se pueda volver atrás sin loguearse
            }
        });
    }
}
