# Fase 1: Construcción con Maven
FROM maven:3.8-openjdk-17 AS builder
WORKDIR /app

# Copiar los archivos necesarios para resolver dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el resto del proyecto y compilar
COPY . .
RUN mvn clean package -DskipTests

# Fase 2: Imagen final para ejecución
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de la aplicación al contenedor
COPY --from=builder /app/target/SI_GESTOR_CLUB_DEPORTIVO-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que la aplicación escucha
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]