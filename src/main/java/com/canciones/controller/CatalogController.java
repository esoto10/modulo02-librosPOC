package com.canciones.controller;

import com.canciones.dto.AlbumInput;
import com.canciones.dto.ArtistaInput;
import com.canciones.dto.CancionInput;
import com.canciones.model.Album;
import com.canciones.model.Artist;
import com.canciones.model.Genre;
import com.canciones.model.Song;
import com.canciones.service.CatalogService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Controlador GraphQL del Catálogo de Canciones.
 *
 * Expone los resolvers que conectan el schema GraphQL con la capa de servicio.
 * Cada método se mapea a un tipo de operación definido en schema.graphqls:
 *  - @QueryMapping  → Query.{nombreMétodo}
 *  - @MutationMapping → Mutation.{nombreMétodo}
 *  - @Argument      → Bindea un argumento GraphQL al parámetro Java (incluye Records)
 *
 * El framework Spring GraphQL resuelve automáticamente los campos
 * de los tipos anidados (Artista, Album, Genero) a través de los
 * getters de las entidades JPA retornadas.
 */
@Controller
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    // ====================================================================
    // QUERIES - Operaciones de solo lectura
    // ====================================================================

    /**
     * Resolver para: Query { canciones }
     * Devuelve la lista completa de canciones del catálogo.
     */
    @QueryMapping
    public List<Song> canciones() {
        return catalogService.obtenerCanciones();
    }

    /**
     * Resolver para: Query { cancion(id: ID!) }
     * Busca una canción por su identificador único.
     *
     * @param id  ID de la canción (mapeado desde el escalar ID de GraphQL)
     * @return    La canción encontrada, o null si no existe
     */
    @QueryMapping
    public Song cancion(@Argument Long id) {
        return catalogService.obtenerCancion(id).orElse(null);
    }

    /**
     * Resolver para: Query { artistas }
     * Devuelve la lista completa de artistas.
     */
    @QueryMapping
    public List<Artist> artistas() {
        return catalogService.obtenerArtistas();
    }

    /**
     * Resolver para: Query { artista(id: ID!) }
     * Busca un artista por su identificador único.
     *
     * @param id  ID del artista
     * @return    El artista encontrado, o null si no existe
     */
    @QueryMapping
    public Artist artista(@Argument Long id) {
        return catalogService.obtenerArtista(id).orElse(null);
    }

    /**
     * Resolver para: Query { albumes }
     * Devuelve la lista completa de álbumes.
     */
    @QueryMapping
    public List<Album> albumes() {
        return catalogService.obtenerAlbumes();
    }

    /**
     * Resolver para: Query { album(id: ID!) }
     * Busca un álbum por su identificador único.
     *
     * @param id  ID del álbum
     * @return    El álbum encontrado, o null si no existe
     */
    @QueryMapping
    public Album album(@Argument Long id) {
        return catalogService.obtenerAlbum(id).orElse(null);
    }

    /**
     * Resolver para: Query { generos }
     * Devuelve la lista completa de géneros musicales.
     */
    @QueryMapping
    public List<Genre> generos() {
        return catalogService.obtenerGeneros();
    }

    // ====================================================================
    // MUTATIONS - Operaciones que modifican datos
    // ====================================================================

    /**
     * Resolver para: Mutation { agregarCancion(input: CancionInput!) }
     * Crea una nueva canción en el catálogo.
     *
     * @param input  Record Java con los datos de la canción,
     *               binding automático desde el Input Type GraphQL CancionInput
     * @return       La canción creada con su ID asignado
     */
    @MutationMapping
    public Song agregarCancion(@Argument CancionInput input) {
        return catalogService.agregarCancion(input);
    }

    /**
     * Resolver para: Mutation { eliminarCancion(id: ID!) }
     * Elimina una canción del catálogo por su ID.
     *
     * @param id  ID de la canción a eliminar
     * @return    true si fue eliminada exitosamente; false si no existía
     */
    @MutationMapping
    public boolean eliminarCancion(@Argument Long id) {
        return catalogService.eliminarCancion(id);
    }

    /**
     * Resolver para: Mutation { agregarArtista(input: ArtistaInput!) }
     * Crea un nuevo artista en el catálogo.
     *
     * @param input  Record Java con los datos del artista
     * @return       El artista creado con su ID asignado
     */
    @MutationMapping
    public Artist agregarArtista(@Argument ArtistaInput input) {
        return catalogService.agregarArtista(input);
    }

    /**
     * Resolver para: Mutation { agregarAlbum(input: AlbumInput!) }
     * Crea un nuevo álbum asociado a un artista existente.
     *
     * @param input  Record Java con los datos del álbum
     * @return       El álbum creado con su ID asignado
     */
    @MutationMapping
    public Album agregarAlbum(@Argument AlbumInput input) {
        return catalogService.agregarAlbum(input);
    }
}
