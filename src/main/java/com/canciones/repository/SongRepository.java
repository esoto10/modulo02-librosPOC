package com.canciones.repository;

import com.canciones.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio Spring Data JPA para la entidad Song.
 * Hereda operaciones CRUD y de paginación de JpaRepository.
 */
@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
}
