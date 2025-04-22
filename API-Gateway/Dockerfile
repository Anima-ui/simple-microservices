FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/API-Gateway-0.0.1-SNAPSHOT.jar ./API-Gateway.jar
COPY src/main/resources/application.yaml ./application.yaml
ENTRYPOINT ["java", "-jar", "API-Gateway.jar"]