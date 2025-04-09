package com.example.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.entities.Producto;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Producto> productList;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private String username;

    public ProductAdapter(List<Producto> productList, String username) {
        this.productList = productList;
        this.username = username;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Producto product = productList.get(position);
        holder.name.setText(product.getName());
        holder.description.setText(product.getDescription());
        holder.image.setImageResource(product.getImageResource());
        holder.price.setText("Precio: $" + product.getPrecio());
        holder.quantity.setText(String.valueOf(product.getCantidad()));

        holder.btnPlus.setOnClickListener(v -> {
            int cantidad = product.getCantidad() + 1;
            product.setCantidad(cantidad);
            holder.quantity.setText(String.valueOf(cantidad));
        });

        holder.btnMinus.setOnClickListener(v -> {
            int cantidad = product.getCantidad();
            if (cantidad > 0) {
                cantidad--;
                product.setCantidad(cantidad);
                holder.quantity.setText(String.valueOf(cantidad));
            }
        });

        holder.btnAgregar.setOnClickListener(v -> {
            if (product.getCantidad() > 0) {
                Map<String, Object> compra = new HashMap<>();
                compra.put("usuario", username);
                compra.put("producto", product.getName());
                compra.put("descripcion", product.getDescription());
                compra.put("precioUnitario", product.getPrecio());
                compra.put("cantidad", product.getCantidad());
                compra.put("total", product.getCantidad() * product.getPrecio());

                firestore.collection("compras").add(compra);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, price, quantity;
        ImageView image;
        Button btnPlus, btnMinus, btnAgregar;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.product_description);
            image = itemView.findViewById(R.id.product_image);
            price = itemView.findViewById(R.id.product_price);
            quantity = itemView.findViewById(R.id.product_quantity);
            btnPlus = itemView.findViewById(R.id.btn_plus);
            btnMinus = itemView.findViewById(R.id.btn_minus);
            btnAgregar = itemView.findViewById(R.id.btn_agregar);
        }
    }
}
