package com.canciones.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA que representa a un Artista o grupo musical.
 *
 * Relaciones:
 *  - @OneToMany → Album:  un artista puede publicar múltiples álbumes
 *  - @OneToMany → Song:   un artista puede interpretar múltiples canciones
 *
 * Mapeado al tipo GraphQL: Artista
 * Tabla en base de datos:  artistas
 */
@Entity
@Table(name = "artistas")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre artístico o del grupo musical */
    @Column(nullable = false)
    private String nombre;

    /** País o región de origen del artista */
    @Column(nullable = false)
    private String nacionalidad;

    /**
     * Álbumes publicados por este artista.
     * mappedBy = "artista" indica que Album es el lado dueño de la relación.
     */
    @OneToMany(mappedBy = "artista")
    private List<Album> albumes = new ArrayList<>();

    /**
     * Canciones interpretadas por este artista.
     * mappedBy = "artista" indica que Song es el lado dueño de la relación.
     */
    @OneToMany(mappedBy = "artista")
    private List<Song> canciones = new ArrayList<>();

    /** Constructor vacío requerido por JPA/Hibernate */
    public Artist() {}

    /** Constructor de conveniencia para crear un artista con sus campos principales */
    public Artist(String nombre, String nacionalidad) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    // ========================= Getters y Setters =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public List<Album> getAlbumes() {
        return albumes;
    }

    public void setAlbumes(List<Album> albumes) {
        this.albumes = albumes;
    }

    public List<Song> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Song> canciones) {
        this.canciones = canciones;
    }
}
