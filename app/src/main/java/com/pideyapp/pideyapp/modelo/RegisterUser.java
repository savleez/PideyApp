package com.pideyapp.pideyapp.modelo;

public class RegisterUser {

    //Declaracion de variables

    private String Apellidos;
    private String Contraseña;
    private String Correo;
    private String Nombres;
    private int Telefono;

    // COnstructor

    public RegisterUser(){

    }

    //Encapsulacion


    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

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

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public int getTelefono() {
        return Telefono;
    }

    public void setTelefono(int telefono) {
        Telefono = telefono;
    }

    //Metodo to String


    @Override
    public String toString() {
        return "RegisterUser{" +
                "Apellidos='" + Apellidos + '\'' +
                ", Contraseña='" + Contraseña + '\'' +
                ", Correo='" + Correo + '\'' +
                ", Nombres='" + Nombres + '\'' +
                ", Telefono=" + Telefono +
                '}';
    }
}
