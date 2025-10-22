# this has both mvn and jdk , it is called builder(stage 1)
FROM maven:3.9.6-eclipse-temurin-17 AS builder

COPY pom.xml .
COPY src ./src
# we are asking jenkins to run tests alone separately
RUN mvn clean package -DskipTests

# second stage (the previous stage is discarded i guess(not sure)
FROM eclipse-temurin:17-jre

WORKDIR /app

# from the stage named builder copy and docker renames the compiled jar to app.jar
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]