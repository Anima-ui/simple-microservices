FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/notificationservice-0.0.1-SNAPSHOT.jar ./notificationservice.jar
CMD ["java", "-jar", "notificationservice.jar"]