FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/storehouse-0.0.1-SNAPSHOT.jar ./storehouse.jar
CMD ["java", "-jar", "storehouse.jar"]