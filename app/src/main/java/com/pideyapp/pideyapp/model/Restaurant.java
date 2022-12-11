package com.pideyapp.pideyapp.model;

import java.util.Objects;

public class Restaurant {
    private String nombre;
    private String nit;
    private String telefono;
    private String ciudad;
    private String email;
    private String password;

    public Restaurant() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(nombre, that.nombre) && Objects.equals(nit, that.nit) && Objects.equals(telefono, that.telefono) && Objects.equals(ciudad, that.ciudad) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, nit, telefono, ciudad, email, password);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "nombre='" + nombre + '\'' +
                ", nit='" + nit + '\'' +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }
}
