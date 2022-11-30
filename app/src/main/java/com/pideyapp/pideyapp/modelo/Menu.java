package com.pideyapp.pideyapp.modelo;

public class Menu {

    //declaracion de variables

    private String Descripcion;
    private String Plato;
    private int Precio;
    private String Restaurante;

    // Constructor

    public Menu(){

    }

    //Encapsulacion


    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getPlato() {
        return Plato;
    }

    public void setPlato(String plato) {
        Plato = plato;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int precio) {
        Precio = precio;
    }

    public String getRestaurante() {
        return Restaurante;
    }

    public void setRestaurante(String restaurante) {
        Restaurante = restaurante;
    }

    //Metodo ToString


    @Override
    public String toString() {
        return "Menu{" +
                "Descripcion='" + Descripcion + '\'' +
                ", Plato='" + Plato + '\'' +
                ", Precio=" + Precio +
                ", Restaurante='" + Restaurante + '\'' +
                '}';
    }
}
