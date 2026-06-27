package com.canciones.repository;

import com.canciones.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio Spring Data JPA para la entidad Genre.
 * Hereda operaciones CRUD y de paginación de JpaRepository.
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
