package com.example.tesis_rra.ListViewObjetos;

public class ModelObjeto {

    String id, titulo, descripcion, link;

    public ModelObjeto(){

    }

    public ModelObjeto(String id, String titulo, String descripcion, String link) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
