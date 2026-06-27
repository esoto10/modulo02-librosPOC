package com.canciones.repository;

import com.canciones.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio Spring Data JPA para la entidad Album.
 * Hereda operaciones CRUD y de paginación de JpaRepository.
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
}
