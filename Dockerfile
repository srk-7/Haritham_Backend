# Stage 1: Build using a reliable JDK
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app
COPY pom.xml .
COPY src src
COPY mvnw .
COPY .mvn .mvn

RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Final image
FROM eclipse-temurin:17-jdk

VOLUME /tmp

# Copy the JAR
COPY --from=build /app/target/*.jar app.jar

# Set timezone (optional but good practice)
ENV TZ=UTC

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
