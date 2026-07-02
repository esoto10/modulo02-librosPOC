package com.libros.service;

import com.libros.dto.AutorInput;
import com.libros.dto.ColeccionInput;
import com.libros.dto.LibroInput;
import com.libros.model.Autor;
import com.libros.model.Categoria;
import com.libros.model.Coleccion;
import com.libros.model.Libro;
import com.libros.repository.AutorRepository;
import com.libros.repository.CategoriaRepository;
import com.libros.repository.ColeccionRepository;
import com.libros.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BibliotecaService {

    private final LibroRepository     libroRepository;
    private final AutorRepository     autorRepository;
    private final ColeccionRepository coleccionRepository;
    private final CategoriaRepository categoriaRepository;

    public BibliotecaService(LibroRepository libroRepository,
                             AutorRepository autorRepository,
                             ColeccionRepository coleccionRepository,
                             CategoriaRepository categoriaRepository) {
        this.libroRepository     = libroRepository;
        this.autorRepository     = autorRepository;
        this.coleccionRepository = coleccionRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<Libro> obtenerLibros() { return libroRepository.findAll(); }

    @Transactional(readOnly = true)
    public List<Libro> obtenerLibrosPorCategoria(String nombre) {
        return categoriaRepository.findByNombreIgnoreCase(nombre)
                .map(Categoria::getLibros)
                .orElse(List.of());
    }

    @Transactional(readOnly = true)
    public Optional<Libro> obtenerLibro(Long id) { return libroRepository.findById(id); }

    public Libro agregarLibro(LibroInput input) {
        Autor autor = autorRepository.findById(input.autorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado: " + input.autorId()));
        Coleccion coleccion = null;
        if (input.coleccionId() != null) {
            coleccion = coleccionRepository.findById(input.coleccionId())
                    .orElseThrow(() -> new IllegalArgumentException("Colección no encontrada: " + input.coleccionId()));
        }
        List<Categoria> categorias = new ArrayList<>();
        if (input.categoriaIds() != null && !input.categoriaIds().isEmpty()) {
            categorias = categoriaRepository.findAllById(input.categoriaIds());
        }
        Libro libro = new Libro(input.titulo(), input.paginas(), input.anio(), autor, coleccion);
        libro.setCategorias(categorias);
        return libroRepository.save(libro);
    }

    public boolean eliminarLibro(Long id) {
        if (!libroRepository.existsById(id)) return false;
        libroRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public List<Autor> obtenerAutores() { return autorRepository.findAll(); }

    @Transactional(readOnly = true)
    public Optional<Autor> obtenerAutor(Long id) { return autorRepository.findById(id); }

    public Autor agregarAutor(AutorInput input) {
        return autorRepository.save(new Autor(input.nombre(), input.nacionalidad()));
    }

    @Transactional(readOnly = true)
    public List<Coleccion> obtenerColecciones() { return coleccionRepository.findAll(); }

    @Transactional(readOnly = true)
    public Optional<Coleccion> obtenerColeccion(Long id) { return coleccionRepository.findById(id); }

    public Coleccion agregarColeccion(ColeccionInput input) {
        Autor autor = autorRepository.findById(input.autorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado: " + input.autorId()));
        return coleccionRepository.save(new Coleccion(input.titulo(), input.anio(), autor));
    }

    @Transactional(readOnly = true)
    public List<Categoria> obtenerCategorias() { return categoriaRepository.findAll(); }
}
