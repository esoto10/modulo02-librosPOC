# ============================================================
# Dockerfile - Catálogo de Canciones POC
# Build multi-etapa para imagen optimizada con Java 21
# ============================================================

# ------------------------------------------------------------------
# ETAPA 1: BUILD
# Usa la imagen oficial de Maven con JDK 21 para compilar el proyecto
# ------------------------------------------------------------------
FROM maven:3.9.7-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar solo el pom.xml primero para aprovechar la caché de capas Docker.
# Si el código fuente cambia pero las dependencias no, Maven no las vuelve a descargar.
COPY pom.xml .
RUN mvn dependency:go-offline -B --no-transfer-progress

# Copiar el código fuente y compilar el JAR
COPY src ./src
RUN mvn clean package -DskipTests -B --no-transfer-progress

# ------------------------------------------------------------------
# ETAPA 2: RUNTIME
# Imagen final liviana: solo JRE (sin Maven ni fuentes)
# eclipse-temurin:21-jre-alpine (~90 MB vs ~500 MB del JDK completo)
# ------------------------------------------------------------------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Metadatos de la imagen
LABEL maintainer="diplomado-arquitectura"
LABEL description="POC Catálogo de Canciones - Spring Boot + GraphQL"
LABEL version="0.0.1-SNAPSHOT"

# Crear usuario no-root por seguridad (buena práctica OWASP)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copiar el JAR generado en la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto configurado en application.properties
EXPOSE 8085

# Punto de entrada: ejecutar el JAR con configuración de memoria adecuada para contenedores
ENTRYPOINT ["java", \
            "-XX:+UseContainerSupport", \
            "-XX:MaxRAMPercentage=75.0", \
            "-jar", "app.jar"]
