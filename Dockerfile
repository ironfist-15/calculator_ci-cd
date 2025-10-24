# Use a lightweight OpenJDK runtime image
FROM eclipse-temurin:17-jre-jammy

# Set working directory inside the container
WORKDIR /app

# Copy the prebuilt JAR from Jenkins build artifacts
COPY target/calculator-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
