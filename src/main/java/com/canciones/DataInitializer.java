package com.canciones;

import com.canciones.model.Album;
import com.canciones.model.Artist;
import com.canciones.model.Genre;
import com.canciones.model.Song;
import com.canciones.repository.AlbumRepository;
import com.canciones.repository.ArtistRepository;
import com.canciones.repository.GenreRepository;
import com.canciones.repository.SongRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Componente que carga datos de prueba al iniciar la aplicación.
 *
 * Implementa ApplicationRunner: se ejecuta automáticamente después
 * de que el contexto de Spring esté completamente inicializado.
 *
 * Datos cargados:
 *  - 5 Géneros musicales
 *  - 5 Artistas reales (con su nacionalidad)
 *  - 5 Álbumes reales
 *  - 15 Canciones reales (3 por artista)
 *
 * La anotación @Transactional garantiza que todas las operaciones
 * de persistencia ocurran dentro de una única transacción JPA,
 * evitando problemas con entidades en estado detached.
 */
@Component
public class DataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final ArtistRepository artistRepository;
    private final AlbumRepository  albumRepository;
    private final SongRepository   songRepository;
    private final GenreRepository  genreRepository;

    /** Inyección de dependencias por constructor */
    public DataInitializer(ArtistRepository artistRepository,
                           AlbumRepository albumRepository,
                           SongRepository songRepository,
                           GenreRepository genreRepository) {
        this.artistRepository = artistRepository;
        this.albumRepository  = albumRepository;
        this.songRepository   = songRepository;
        this.genreRepository  = genreRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        log.info("========================================");
        log.info("   Iniciando carga de datos de prueba   ");
        log.info("========================================");

        // ================================================================
        // GÉNEROS MUSICALES (5 géneros)
        // ================================================================
        Genre rockClasico = genreRepository.save(new Genre("Rock Clásico"));
        Genre pop         = genreRepository.save(new Genre("Pop"));
        Genre rbSoul      = genreRepository.save(new Genre("R&B/Soul"));
        Genre reggae      = genreRepository.save(new Genre("Reggae"));
        Genre popLatino   = genreRepository.save(new Genre("Pop Latino"));

        log.info("✔ Géneros creados: {}", genreRepository.count());

        // ================================================================
        // ARTISTAS (5 artistas con su nacionalidad)
        // ================================================================
        Artist beatles = artistRepository.save(new Artist("The Beatles",       "Británica"));
        Artist jackson = artistRepository.save(new Artist("Michael Jackson",   "Estadounidense"));
        Artist queen   = artistRepository.save(new Artist("Queen",             "Británica"));
        Artist marley  = artistRepository.save(new Artist("Bob Marley",        "Jamaicana"));
        Artist shakira = artistRepository.save(new Artist("Shakira",           "Colombiana"));

        log.info("✔ Artistas creados: {}", artistRepository.count());

        // ================================================================
        // ÁLBUMES (5 álbumes, uno por artista)
        // ================================================================
        Album abbeyRoad      = albumRepository.save(new Album("Abbey Road",             1969, beatles));
        Album thriller       = albumRepository.save(new Album("Thriller",               1982, jackson));
        Album nightAtOpera   = albumRepository.save(new Album("A Night at the Opera",   1975, queen));
        Album legend         = albumRepository.save(new Album("Legend",                 1984, marley));
        Album laundryService = albumRepository.save(new Album("Laundry Service",        2001, shakira));

        log.info("✔ Álbumes creados: {}", albumRepository.count());

        // ================================================================
        // CANCIONES (15 canciones, 3 por artista)
        // ================================================================

        // --- The Beatles (Abbey Road, 1969) ---
        guardarCancion("Come Together",      259, 1969, beatles, abbeyRoad,
                List.of(rockClasico));
        guardarCancion("Something",          182, 1969, beatles, abbeyRoad,
                List.of(rockClasico));
        guardarCancion("Here Comes the Sun", 185, 1969, beatles, abbeyRoad,
                List.of(rockClasico, pop));

        // --- Michael Jackson (Thriller, 1982) ---
        guardarCancion("Thriller",           358, 1982, jackson, thriller,
                List.of(pop, rbSoul));
        guardarCancion("Billie Jean",        294, 1982, jackson, thriller,
                List.of(pop, rbSoul));
        guardarCancion("Beat It",            258, 1982, jackson, thriller,
                List.of(pop, rockClasico));

        // --- Queen (A Night at the Opera, 1975) ---
        guardarCancion("Bohemian Rhapsody",      354, 1975, queen, nightAtOpera,
                List.of(rockClasico));
        guardarCancion("Love of My Life",        213, 1975, queen, nightAtOpera,
                List.of(rockClasico));
        guardarCancion("You're My Best Friend",  170, 1975, queen, nightAtOpera,
                List.of(rockClasico, pop));

        // --- Bob Marley (Legend, 1984) ---
        guardarCancion("No Woman No Cry",    233, 1984, marley, legend,
                List.of(reggae));
        guardarCancion("Redemption Song",    226, 1984, marley, legend,
                List.of(reggae));
        guardarCancion("One Love",           177, 1984, marley, legend,
                List.of(reggae));

        // --- Shakira (Laundry Service, 2001) ---
        guardarCancion("Whenever Wherever",       213, 2001, shakira, laundryService,
                List.of(popLatino, pop));
        guardarCancion("Underneath Your Clothes", 242, 2001, shakira, laundryService,
                List.of(popLatino));
        guardarCancion("Objection (Tango)",       261, 2001, shakira, laundryService,
                List.of(popLatino, pop));

        log.info("✔ Canciones creadas: {}", songRepository.count());
        log.info("========================================");
        log.info("   Datos de prueba cargados con éxito   ");
        log.info("========================================");
        log.info("  GraphiQL disponible en: http://localhost:8080/graphiql");
        log.info("  H2 Console disponible en: http://localhost:8080/h2-console");
        log.info("  JDBC URL H2: jdbc:h2:mem:cancionesdb");
    }

    /**
     * Método auxiliar privado para crear y persistir una canción con sus géneros.
     *
     * @param titulo   Título de la canción
     * @param duracion Duración en segundos
     * @param anio     Año de lanzamiento
     * @param artista  Artista principal
     * @param album    Álbum al que pertenece
     * @param generos  Lista de géneros musicales asociados
     */
    private void guardarCancion(String titulo, int duracion, int anio,
                                Artist artista, Album album, List<Genre> generos) {
        Song cancion = new Song(titulo, duracion, anio, artista, album);
        cancion.setGeneros(new ArrayList<>(generos));
        songRepository.save(cancion);
    }
}
