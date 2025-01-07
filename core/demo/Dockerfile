# Use the correct JDK base image (Java 17 Slim)
FROM openjdk:17-slim as build

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/demo-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

# Define the entry point for the container
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
