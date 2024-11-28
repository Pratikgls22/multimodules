FROM openjdk:17-slim AS build
WORKDIR /app
COPY target/ecom_essentials-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]