package com.libros.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String nacionalidad;

    @OneToMany(mappedBy = "autor")
    private List<Coleccion> colecciones = new ArrayList<>();

    @OneToMany(mappedBy = "autor")
    private List<Libro> libros = new ArrayList<>();

    public Autor() {}

    public Autor(String nombre, String nacionalidad) {
        this.nombre       = nombre;
        this.nacionalidad = nacionalidad;
    }

    public Long getId()                            { return id; }
    public void setId(Long id)                     { this.id = id; }
    public String getNombre()                      { return nombre; }
    public void setNombre(String nombre)           { this.nombre = nombre; }
    public String getNacionalidad()                { return nacionalidad; }
    public void setNacionalidad(String n)          { this.nacionalidad = n; }
    public List<Coleccion> getColecciones()        { return colecciones; }
    public void setColecciones(List<Coleccion> c)  { this.colecciones = c; }
    public List<Libro> getLibros()                 { return libros; }
    public void setLibros(List<Libro> libros)      { this.libros = libros; }
}
