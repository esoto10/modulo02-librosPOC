package com.canciones.service;

import com.canciones.dto.AlbumInput;
import com.canciones.dto.ArtistaInput;
import com.canciones.dto.CancionInput;
import com.canciones.model.Album;
import com.canciones.model.Artist;
import com.canciones.model.Genre;
import com.canciones.model.Song;
import com.canciones.repository.AlbumRepository;
import com.canciones.repository.ArtistRepository;
import com.canciones.repository.GenreRepository;
import com.canciones.repository.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio principal del Catálogo de Canciones.
 *
 * Centraliza toda la lógica de negocio de la aplicación.
 * Es la única capa que interactúa directamente con los repositorios JPA.
 *
 * Gestiona las cuatro entidades del dominio:
 *  - Canciones (Song)
 *  - Artistas  (Artist)
 *  - Álbumes   (Album)
 *  - Géneros   (Genre)
 */
@Service
@Transactional
public class CatalogService {

    private final SongRepository   songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository  albumRepository;
    private final GenreRepository  genreRepository;

    /** Inyección de dependencias por constructor (buena práctica en Spring) */
    public CatalogService(SongRepository songRepository,
                          ArtistRepository artistRepository,
                          AlbumRepository albumRepository,
                          GenreRepository genreRepository) {
        this.songRepository   = songRepository;
        this.artistRepository = artistRepository;
        this.albumRepository  = albumRepository;
        this.genreRepository  = genreRepository;
    }

    // ====================================================================
    // CANCIONES
    // ====================================================================

    /** Devuelve todas las canciones del catálogo */
    @Transactional(readOnly = true)
    public List<Song> obtenerCanciones() {
        return songRepository.findAll();
    }

    /** Busca una canción por su ID */
    @Transactional(readOnly = true)
    public Optional<Song> obtenerCancion(Long id) {
        return songRepository.findById(id);
    }

    /**
     * Crea y persiste una nueva canción en el catálogo.
     *
     * Pasos:
     * 1. Verifica que el artista exista
     * 2. Verifica que el álbum exista (si se proporcionó)
     * 3. Recupera los géneros indicados
     * 4. Crea y guarda la canción
     *
     * @param input DTO con los datos de la nueva canción (Java Record)
     * @return La canción creada con su ID asignado
     * @throws IllegalArgumentException si el artista o álbum no existen
     */
    public Song agregarCancion(CancionInput input) {
        // 1. Buscar el artista (campo obligatorio)
        Artist artista = artistRepository.findById(input.artistaId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Artista no encontrado con ID: " + input.artistaId()));

        // 2. Buscar el álbum (campo opcional)
        Album album = null;
        if (input.albumId() != null) {
            album = albumRepository.findById(input.albumId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Álbum no encontrado con ID: " + input.albumId()));
        }

        // 3. Recuperar la lista de géneros (opcional)
        List<Genre> generos = new ArrayList<>();
        if (input.generoIds() != null && !input.generoIds().isEmpty()) {
            generos = genreRepository.findAllById(input.generoIds());
        }

        // 4. Crear y guardar la canción
        Song cancion = new Song(input.titulo(), input.duracion(), input.anio(), artista, album);
        cancion.setGeneros(generos);
        return songRepository.save(cancion);
    }

    /**
     * Elimina una canción del catálogo por su ID.
     * Las entradas en la tabla de unión cancion_genero se eliminan automáticamente.
     *
     * @param id ID de la canción a eliminar
     * @return true si la canción fue eliminada; false si no existía
     */
    public boolean eliminarCancion(Long id) {
        if (songRepository.existsById(id)) {
            songRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ====================================================================
    // ARTISTAS
    // ====================================================================

    /** Devuelve todos los artistas del catálogo */
    @Transactional(readOnly = true)
    public List<Artist> obtenerArtistas() {
        return artistRepository.findAll();
    }

    /** Busca un artista por su ID */
    @Transactional(readOnly = true)
    public Optional<Artist> obtenerArtista(Long id) {
        return artistRepository.findById(id);
    }

    /**
     * Crea y persiste un nuevo artista.
     *
     * @param input DTO con los datos del artista (Java Record)
     * @return El artista creado con su ID asignado
     */
    public Artist agregarArtista(ArtistaInput input) {
        Artist artista = new Artist(input.nombre(), input.nacionalidad());
        return artistRepository.save(artista);
    }

    // ====================================================================
    // ÁLBUMES
    // ====================================================================

    /** Devuelve todos los álbumes del catálogo */
    @Transactional(readOnly = true)
    public List<Album> obtenerAlbumes() {
        return albumRepository.findAll();
    }

    /** Busca un álbum por su ID */
    @Transactional(readOnly = true)
    public Optional<Album> obtenerAlbum(Long id) {
        return albumRepository.findById(id);
    }

    /**
     * Crea y persiste un nuevo álbum, asociado a un artista existente.
     *
     * @param input DTO con los datos del álbum (Java Record)
     * @return El álbum creado con su ID asignado
     * @throws IllegalArgumentException si el artista no existe
     */
    public Album agregarAlbum(AlbumInput input) {
        Artist artista = artistRepository.findById(input.artistaId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Artista no encontrado con ID: " + input.artistaId()));
        Album album = new Album(input.titulo(), input.anio(), artista);
        return albumRepository.save(album);
    }

    // ====================================================================
    // GÉNEROS
    // ====================================================================

    /** Devuelve todos los géneros musicales */
    @Transactional(readOnly = true)
    public List<Genre> obtenerGeneros() {
        return genreRepository.findAll();
    }
}
