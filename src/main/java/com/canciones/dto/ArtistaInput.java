package com.canciones.dto;

/**
 * DTO de entrada (Input Type) para crear un nuevo Artista.
 *
 * Uso de Java 21 Record:
 *  - Inmutable, conciso y seguro para transferencia de datos
 *
 * Mapeado desde el Input Type GraphQL: ArtistaInput
 *
 * @param nombre        Nombre artístico o del grupo (obligatorio)
 * @param nacionalidad  País o región de origen (obligatorio)
 */
public record ArtistaInput(
        String nombre,
        String nacionalidad
) {}
