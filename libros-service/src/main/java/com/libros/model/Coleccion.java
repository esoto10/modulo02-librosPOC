package com.libros.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "colecciones")
public class Coleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private int anio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @OneToMany(mappedBy = "coleccion")
    private List<Libro> libros = new ArrayList<>();

    public Coleccion() {}

    public Coleccion(String titulo, int anio, Autor autor) {
        this.titulo = titulo;
        this.anio   = anio;
        this.autor  = autor;
    }

    public Long getId()                    { return id; }
    public void setId(Long id)             { this.id = id; }
    public String getTitulo()              { return titulo; }
    public void setTitulo(String titulo)   { this.titulo = titulo; }
    public int getAnio()                   { return anio; }
    public void setAnio(int anio)          { this.anio = anio; }
    public Autor getAutor()                { return autor; }
    public void setAutor(Autor autor)      { this.autor = autor; }
    public List<Libro> getLibros()         { return libros; }
    public void setLibros(List<Libro> l)   { this.libros = l; }
}
