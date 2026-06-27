package com.canciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación POC: Catálogo de Canciones.
 *
 * Stack:
 * - Java 21
 * - Spring Boot 3.x
 * - Spring GraphQL (endpoint: POST /graphql, UI: /graphiql)
 * - Spring Data JPA + Hibernate
 * - H2 Database en memoria (consola: /h2-console)
 */
@SpringBootApplication
public class CancionesPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(CancionesPocApplication.class, args);
    }
}
