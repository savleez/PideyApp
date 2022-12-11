package com.pideyapp.pideyapp.model;

import java.util.Objects;

public class Usuario {
    private String nombres;
    private String apellidos;
    private String documento;
    private String telefono;
    private String email;
    private String password;

    public Usuario() {
    }

    public String getNombres() {
        return nombres;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
        Usuario usuario = (Usuario) o;
        return Objects.equals(nombres, usuario.nombres) && Objects.equals(apellidos, usuario.apellidos) && Objects.equals(telefono, usuario.telefono) && Objects.equals(email, usuario.email) && Objects.equals(password, usuario.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombres, apellidos, telefono, email, password);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "documento='" + documento + '\'' +
                '}';
    }
}
