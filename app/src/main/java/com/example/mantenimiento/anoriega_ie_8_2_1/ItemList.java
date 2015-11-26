package com.example.mantenimiento.anoriega_ie_8_2_1;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;

public class ItemList {

    private String pathImagen;
    private String titulo;
    private String subtitulo;
    private String fecha;
    private String descripcion;

    public ItemList (String pathImagen, String titulo, String subtitulo, String fecha, String descripcion) {
        this.pathImagen = pathImagen;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.fecha=fecha;
        this.descripcion=descripcion;
    }

    public String getPathImagen() {
        return pathImagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public String getFecha()
    {
        return fecha;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setPathImagen(String pathImagen)
    {
        this.pathImagen=pathImagen;
    }

    public void setTitulo(String titulo)
    {
        this.titulo=titulo;
    }

    public void setSubtitulo(String subtitulo)
    {
        this.subtitulo=subtitulo;
    }

    public void setFecha(String fecha)
    {
        this.fecha=fecha;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion=descripcion;
    }

}
