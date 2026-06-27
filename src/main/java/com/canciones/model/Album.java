package com.canciones.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA que representa un Álbum musical.
 *
 * Relaciones:
 *  - @ManyToOne → Artist: cada álbum pertenece a un único artista
 *  - @OneToMany → Song:   un álbum puede contener múltiples canciones
 *
 * Mapeado al tipo GraphQL: Album
 * Tabla en base de datos:  albumes
 */
@Entity
@Table(name = "albumes")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Título del álbum */
    @Column(nullable = false)
    private String titulo;

    /** Año de lanzamiento del álbum */
    @Column(nullable = false)
    private int anio;

    /**
     * Artista al que pertenece este álbum.
     * FetchType.LAZY: se carga solo cuando se accede (más eficiente).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artista_id", nullable = false)
    private Artist artista;

    /**
     * Canciones que componen este álbum.
     * mappedBy = "album" indica que Song es el lado dueño de la relación.
     */
    @OneToMany(mappedBy = "album")
    private List<Song> canciones = new ArrayList<>();

    /** Constructor vacío requerido por JPA/Hibernate */
    public Album() {}

    /** Constructor de conveniencia para crear un álbum */
    public Album(String titulo, int anio, Artist artista) {
        this.titulo = titulo;
        this.anio = anio;
        this.artista = artista;
    }

    // ========================= Getters y Setters =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Artist getArtista() {
        return artista;
    }

    public void setArtista(Artist artista) {
        this.artista = artista;
    }

    public List<Song> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Song> canciones) {
        this.canciones = canciones;
    }
}
