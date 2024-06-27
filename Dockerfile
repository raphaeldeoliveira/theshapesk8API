# Estágio de build
FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

WORKDIR /app

COPY . .

RUN mvn clean install -e

# Estágio de produção
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/theshapesk8API-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
