package com.pideyapp.pideyapp.modelo;

public class RegisterRestaurant {

    //declaracion de variables

    private String Contraseña;
    private String Correo;
    private String Direccion;
    private String Nit;
    private String Nombre;
    private int Telefono;

    // Constructor

    public RegisterRestaurant(){

    }

    //Encapsulacion


    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getNit() {
        return Nit;
    }

    public void setNit(String nit) {
        Nit = nit;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getTelefono() {
        return Telefono;
    }

    public void setTelefono(int telefono) {
        Telefono = telefono;
    }

    // Metodo toString


    @Override
    public String toString() {
        return "RegisterRestaurant{" +
                "Contraseña='" + Contraseña + '\'' +
                ", Correo='" + Correo + '\'' +
                ", Direccion='" + Direccion + '\'' +
                ", Nit='" + Nit + '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", Telefono=" + Telefono +
                '}';
    }
}
