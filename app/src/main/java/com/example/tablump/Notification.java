package com.example.tablump;

public class Notification {

    private String tipo;
    private String titulo;
    private String usuarioRecibe;
    private String usuarioRealiza;

    public Notification(String tipo, String titulo, String usuarioRecibe, String usuarioRealiza) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.usuarioRecibe = usuarioRecibe;
        this.usuarioRealiza = usuarioRealiza;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUsuarioRecibe() {
        return usuarioRecibe;
    }

    public void setUsuarioRecibe(String usuarioRecibe) {
        this.usuarioRecibe = usuarioRecibe;
    }

    public String getUsuarioRealiza() {
        return usuarioRealiza;
    }

    public void setUsuarioRealiza(String usuarioRealiza) {
        this.usuarioRealiza = usuarioRealiza;
    }
}
