
#build stage
FROM maven:3.8.4-openjdk-17 AS build
#working directory
WORKDIR /app
#copy depencdency
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests


#Running stage
FROM openjdk:17-jdk-slim

 #Define few things
#ARG PROFILE=dev

WORKDIR /app
COPY --from=build /app/target/e-commerce-1.0.0.jar app.jar


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]