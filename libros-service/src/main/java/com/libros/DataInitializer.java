package com.libros;

import com.libros.model.Autor;
import com.libros.model.Categoria;
import com.libros.model.Coleccion;
import com.libros.model.Libro;
import com.libros.repository.AutorRepository;
import com.libros.repository.CategoriaRepository;
import com.libros.repository.ColeccionRepository;
import com.libros.repository.LibroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final AutorRepository     autorRepository;
    private final ColeccionRepository coleccionRepository;
    private final LibroRepository     libroRepository;
    private final CategoriaRepository categoriaRepository;

    public DataInitializer(AutorRepository autorRepository,
                           ColeccionRepository coleccionRepository,
                           LibroRepository libroRepository,
                           CategoriaRepository categoriaRepository) {
        this.autorRepository     = autorRepository;
        this.coleccionRepository = coleccionRepository;
        this.libroRepository     = libroRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        log.info("=== Cargando datos de prueba: Catálogo de Libros ===");

        // Categorías
        Categoria realismoMagico = categoriaRepository.save(new Categoria("Realismo Mágico"));
        Categoria fantasia       = categoriaRepository.save(new Categoria("Fantasía"));
        Categoria terror         = categoriaRepository.save(new Categoria("Terror"));
        Categoria romance        = categoriaRepository.save(new Categoria("Romance"));
        Categoria aventura       = categoriaRepository.save(new Categoria("Aventura"));

        // Autores
        Autor garcia  = autorRepository.save(new Autor("Gabriel García Márquez", "Colombiana"));
        Autor rowling = autorRepository.save(new Autor("J.K. Rowling",           "Británica"));
        Autor tolkien = autorRepository.save(new Autor("J.R.R. Tolkien",         "Sudafricana"));
        Autor allende = autorRepository.save(new Autor("Isabel Allende",          "Chilena"));
        Autor king    = autorRepository.save(new Autor("Stephen King",            "Estadounidense"));

        // Colecciones
        Coleccion macondo      = coleccionRepository.save(new Coleccion("Ciclo de Macondo",        1967, garcia));
        Coleccion harryPotter  = coleccionRepository.save(new Coleccion("Harry Potter",            1997, rowling));
        Coleccion senorAnillos = coleccionRepository.save(new Coleccion("El Señor de los Anillos", 1954, tolkien));
        Coleccion memorias     = coleccionRepository.save(new Coleccion("Memorias del Alma",       1982, allende));
        Coleccion torreOscura  = coleccionRepository.save(new Coleccion("La Torre Oscura",         1982, king));

        // Libros — García Márquez
        guardar("Cien Años de Soledad",               432, 1967, garcia,  macondo,      List.of(realismoMagico));
        guardar("El Amor en los Tiempos del Cólera",  348, 1985, garcia,  macondo,      List.of(realismoMagico, romance));
        guardar("Crónica de una Muerte Anunciada",    198, 1981, garcia,  macondo,      List.of(realismoMagico));

        // Libros — Rowling
        guardar("Harry Potter y la Piedra Filosofal",      309, 1997, rowling, harryPotter,  List.of(fantasia, aventura));
        guardar("Harry Potter y la Cámara Secreta",        341, 1998, rowling, harryPotter,  List.of(fantasia, aventura));
        guardar("Harry Potter y el Prisionero de Azkaban", 435, 1999, rowling, harryPotter,  List.of(fantasia, aventura));

        // Libros — Tolkien
        guardar("El Hobbit",               310, 1937, tolkien, senorAnillos, List.of(fantasia, aventura));
        guardar("La Comunidad del Anillo", 479, 1954, tolkien, senorAnillos, List.of(fantasia, aventura));
        guardar("Las Dos Torres",          352, 1954, tolkien, senorAnillos, List.of(fantasia, aventura));

        // Libros — Allende
        guardar("La Casa de los Espíritus", 433, 1982, allende, memorias, List.of(realismoMagico));
        guardar("De Amor y de Sombra",      297, 1984, allende, memorias, List.of(romance, realismoMagico));
        guardar("Eva Luna",                 320, 1987, allende, memorias, List.of(realismoMagico, aventura));

        // Libros — King
        guardar("El Pistolero",        224, 1982, king, torreOscura, List.of(terror, fantasia));
        guardar("El Desafío",          400, 1987, king, torreOscura, List.of(terror, fantasia));
        guardar("Las Tierras Baldías", 512, 1991, king, torreOscura, List.of(terror, fantasia));

        log.info("✔ {} libros cargados. GraphiQL: http://localhost:8085/graphiql", libroRepository.count());
    }

    private void guardar(String titulo, int paginas, int anio,
                         Autor autor, Coleccion coleccion, List<Categoria> categorias) {
        Libro libro = new Libro(titulo, paginas, anio, autor, coleccion);
        libro.setCategorias(new ArrayList<>(categorias));
        libroRepository.save(libro);
    }
}
