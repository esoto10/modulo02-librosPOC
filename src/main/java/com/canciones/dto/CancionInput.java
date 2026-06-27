package com.canciones.dto;

import java.util.List;

/**
 * DTO de entrada (Input Type) para crear una nueva Canción.
 *
 * Uso de Java 21 Record:
 *  - Inmutable por diseño (campos final)
 *  - Constructor canónico auto-generado
 *  - Métodos accessor (titulo(), duracion(), etc.) auto-generados
 *
 * Mapeado desde el Input Type GraphQL: CancionInput
 * Binding realizado por Spring GraphQL via @Argument en el controlador.
 *
 * @param titulo     Título de la canción (obligatorio)
 * @param duracion   Duración en segundos (obligatorio)
 * @param anio       Año de lanzamiento (obligatorio)
 * @param artistaId  ID del artista principal (obligatorio)
 * @param albumId    ID del álbum (opcional, puede ser null)
 * @param generoIds  Lista de IDs de géneros (opcional, puede ser null o vacía)
 */
public record CancionInput(
        String titulo,
        int duracion,
        int anio,
        Long artistaId,
        Long albumId,
        List<Long> generoIds
) {}
