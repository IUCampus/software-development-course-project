# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-26 AS build

WORKDIR /app

# Copy Maven descriptor first (better layer caching)
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw.cmd ./

# Download dependencies (will be cached if pom.xml doesn't change)
# Added -Dhttp.keepAlive=false to prevent potential hangs on some networks
RUN chmod +x mvnw && ./mvnw -B dependency:go-offline -Dhttp.keepAlive=false

# Copy the rest of the source code and build
COPY src ./src
RUN ./mvnw -B -q clean package -DskipTests

# ---- Run stage ----
FROM eclipse-temurin:26-jre

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the Spring Boot port (keep in sync with application / docker-compose)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]