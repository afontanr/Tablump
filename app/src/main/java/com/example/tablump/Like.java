package com.example.tablump;

public class Like {

    private String titulo;
    private String usuario;

    public Like(String titulo, String usuario) {
        this.titulo = titulo;
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
