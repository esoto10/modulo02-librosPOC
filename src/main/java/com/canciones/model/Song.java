package com.canciones.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA que representa una Canción del catálogo.
 *
 * Relaciones:
 *  - @ManyToOne → Artist: cada canción tiene un artista principal
 *  - @ManyToOne → Album:  cada canción puede pertenecer a un álbum (opcional)
 *  - @ManyToMany → Genre: una canción puede pertenecer a múltiples géneros.
 *    Song ES el lado DUEÑO de esta relación (posee la tabla join).
 *
 * Mapeado al tipo GraphQL: Cancion
 * Tabla en base de datos:  canciones
 * Tabla join (géneros):    cancion_genero
 */
@Entity
@Table(name = "canciones")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Título de la canción */
    @Column(nullable = false)
    private String titulo;

    /** Duración de la canción expresada en segundos */
    @Column(nullable = false)
    private int duracion;

    /** Año de lanzamiento de la canción */
    @Column(nullable = false)
    private int anio;

    /**
     * Artista principal de la canción.
     * Relación ManyToOne: muchas canciones → un artista.
     * FetchType.LAZY: se carga solo cuando se accede.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artista_id", nullable = false)
    private Artist artista;

    /**
     * Álbum al que pertenece la canción.
     * Relación ManyToOne: muchas canciones → un álbum.
     * Nullable: una canción puede existir sin pertenecer a un álbum.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = true)
    private Album album;

    /**
     * Géneros musicales asociados a esta canción.
     * Relación ManyToMany: Song es el lado DUEÑO de la relación.
     * La tabla de unión se llama "cancion_genero".
     */
    @ManyToMany
    @JoinTable(
        name = "cancion_genero",
        joinColumns = @JoinColumn(name = "cancion_id"),
        inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private List<Genre> generos = new ArrayList<>();

    /** Constructor vacío requerido por JPA/Hibernate */
    public Song() {}

    /** Constructor de conveniencia para crear una canción */
    public Song(String titulo, int duracion, int anio, Artist artista, Album album) {
        this.titulo = titulo;
        this.duracion = duracion;
        this.anio = anio;
        this.artista = artista;
        this.album = album;
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

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Genre> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genre> generos) {
        this.generos = generos;
    }
}
