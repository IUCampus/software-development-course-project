# 1. Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
# Render will assign a PORT environment variable, Spring Boot uses SERVER_PORT.
ENV SERVER_PORT=${PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]