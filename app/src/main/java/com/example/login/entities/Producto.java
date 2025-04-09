package com.example.login.entities;

public class Producto {
    private String name;
    private String description;
    private int imageResource;
    private double precio;
    private int cantidad;

    public Producto() {} // Necesario para Firebase

    public Producto(String name, String description, int imageResource, double precio) {
        this.name = name;
        this.description = description;
        this.imageResource = imageResource;
        this.precio = precio;
        this.cantidad = 0;
    }

    // Getters y setters

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getImageResource() { return imageResource; }
    public void setImageResource(int imageResource) { this.imageResource = imageResource; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
