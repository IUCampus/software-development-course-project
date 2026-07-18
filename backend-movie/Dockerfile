# 1. Build stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose a default port (optional, but good practice for local testing)
EXPOSE 8082

# Use 'sh -c' to ensure the runtime $PORT environment variable is passed to Spring Boot
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8082} -jar app.jar"]