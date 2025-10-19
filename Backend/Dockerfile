FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN mvn dependency:go-offline


EXPOSE 8080

CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.fork=false"]