package com.libros.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private int paginas;

    @Column(nullable = false)
    private int anio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coleccion_id")
    private Coleccion coleccion;

    @ManyToMany
    @JoinTable(
        name = "libro_categoria",
        joinColumns = @JoinColumn(name = "libro_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias = new ArrayList<>();

    public Libro() {}

    public Libro(String titulo, int paginas, int anio, Autor autor, Coleccion coleccion) {
        this.titulo    = titulo;
        this.paginas   = paginas;
        this.anio      = anio;
        this.autor     = autor;
        this.coleccion = coleccion;
    }

    public Long getId()                          { return id; }
    public void setId(Long id)                   { this.id = id; }
    public String getTitulo()                    { return titulo; }
    public void setTitulo(String titulo)         { this.titulo = titulo; }
    public int getPaginas()                      { return paginas; }
    public void setPaginas(int paginas)          { this.paginas = paginas; }
    public int getAnio()                         { return anio; }
    public void setAnio(int anio)                { this.anio = anio; }
    public Autor getAutor()                      { return autor; }
    public void setAutor(Autor autor)            { this.autor = autor; }
    public Coleccion getColeccion()              { return coleccion; }
    public void setColeccion(Coleccion c)        { this.coleccion = c; }
    public List<Categoria> getCategorias()       { return categorias; }
    public void setCategorias(List<Categoria> c) { this.categorias = c; }
}
