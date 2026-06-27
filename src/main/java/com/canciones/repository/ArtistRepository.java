package com.canciones.repository;

import com.canciones.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio Spring Data JPA para la entidad Artist.
 * Hereda operaciones CRUD y de paginación de JpaRepository.
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
