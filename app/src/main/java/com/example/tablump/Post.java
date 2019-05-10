package com.example.tablump;

public class Post {

    private String titulo;
    private String descripcion;
    private String usuario;

    public Post(String titulo, String descripcion, String usuario) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
