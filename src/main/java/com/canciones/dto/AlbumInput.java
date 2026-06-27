package com.canciones.dto;

/**
 * DTO de entrada (Input Type) para crear un nuevo Álbum.
 *
 * Uso de Java 21 Record:
 *  - Inmutable, conciso y seguro para transferencia de datos
 *
 * Mapeado desde el Input Type GraphQL: AlbumInput
 *
 * @param titulo     Título del álbum (obligatorio)
 * @param anio       Año de lanzamiento (obligatorio)
 * @param artistaId  ID del artista al que pertenece el álbum (obligatorio)
 */
public record AlbumInput(
        String titulo,
        int anio,
        Long artistaId
) {}
