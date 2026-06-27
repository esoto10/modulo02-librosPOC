package com.libros.dto;

import java.util.List;

public record LibroInput(
        String titulo,
        int paginas,
        int anio,
        Long autorId,
        Long coleccionId,
        List<Long> categoriaIds
) {}
