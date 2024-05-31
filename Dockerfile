FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.2-jdk-slim
COPY --from=build /target/Movie-Library-0.0.1-SNAPSHOT.jar Movie-Library.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","Movie-Library.jar"]