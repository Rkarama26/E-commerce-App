#build stage
FROM maven:3.8.4-openjdk-17 AS build
#working directory
WORKDIR /build
#copy depencdency
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests


#Running stage
FROM openjdk:17-jdk-slim

 #Define few things
ARG PROFILE=dev

WORKDIR /app
COPY --from=build /build/target/e-commerce-1.0.0.jar /app/

EXPOSE 8080

ENV DB_URL=jdbc:mysql://mysql:3306/ecommerce
ENV ACTIVE_PROFILE=${PROFILE}

CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} /app/e-commerce-1.0.0.jar
