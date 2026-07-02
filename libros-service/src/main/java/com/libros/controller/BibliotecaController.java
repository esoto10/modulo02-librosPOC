package com.libros.controller;

import com.libros.dto.AutorInput;
import com.libros.dto.ColeccionInput;
import com.libros.dto.LibroInput;
import com.libros.model.Autor;
import com.libros.model.Categoria;
import com.libros.model.Coleccion;
import com.libros.model.Libro;
import com.libros.service.BibliotecaService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    public BibliotecaController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    @QueryMapping
    public List<Libro> libros() { return bibliotecaService.obtenerLibros(); }

    @QueryMapping
    public Optional<Libro> libro(@Argument Long id) { return bibliotecaService.obtenerLibro(id); }

    @QueryMapping
    public List<Autor> autores() { return bibliotecaService.obtenerAutores(); }

    @QueryMapping
    public Optional<Autor> autor(@Argument Long id) { return bibliotecaService.obtenerAutor(id); }

    @QueryMapping
    public List<Coleccion> colecciones() { return bibliotecaService.obtenerColecciones(); }

    @QueryMapping
    public Optional<Coleccion> coleccion(@Argument Long id) { return bibliotecaService.obtenerColeccion(id); }

    @QueryMapping
    public List<Categoria> categorias() { return bibliotecaService.obtenerCategorias(); }

    @QueryMapping
    public List<Libro> librosPorCategoria(@Argument String nombre) {
        return bibliotecaService.obtenerLibrosPorCategoria(nombre);
    }

    @MutationMapping
    public Libro agregarLibro(@Argument LibroInput input) { return bibliotecaService.agregarLibro(input); }

    @MutationMapping
    public boolean eliminarLibro(@Argument Long id) { return bibliotecaService.eliminarLibro(id); }

    @MutationMapping
    public Autor agregarAutor(@Argument AutorInput input) { return bibliotecaService.agregarAutor(input); }

    @MutationMapping
    public Coleccion agregarColeccion(@Argument ColeccionInput input) { return bibliotecaService.agregarColeccion(input); }
}
