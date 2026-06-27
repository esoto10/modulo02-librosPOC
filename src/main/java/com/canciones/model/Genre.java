package com.canciones.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA que representa un Género musical.
 *
 * Relaciones:
 *  - @ManyToMany (inversa) → Song: un género puede asociarse a muchas canciones.
 *    Song es el lado dueño de la relación ManyToMany.
 *
 * Mapeado al tipo GraphQL: Genero
 * Tabla en base de datos:  generos
 */
@Entity
@Table(name = "generos")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del género musical (ej: Rock Clásico, Pop, Reggae) */
    @Column(nullable = false, unique = true)
    private String nombre;

    /**
     * Canciones asociadas a este género.
     * mappedBy = "generos" indica que Song.generos es el lado dueño de la relación.
     * Esta colección se carga de forma lazy (por defecto en @ManyToMany).
     */
    @ManyToMany(mappedBy = "generos")
    private List<Song> canciones = new ArrayList<>();

    /** Constructor vacío requerido por JPA/Hibernate */
    public Genre() {}

    /** Constructor de conveniencia */
    public Genre(String nombre) {
        this.nombre = nombre;
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

    public List<Song> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Song> canciones) {
        this.canciones = canciones;
    }
}
