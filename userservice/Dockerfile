FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY src/ src
COPY pom.xml ./
RUN mvn package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/userservice-0.0.1-SNAPSHOT.jar ./userservice.jar
CMD ["java", "-jar", "userservice.jar"]