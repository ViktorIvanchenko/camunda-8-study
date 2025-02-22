# Use a Maven image to build the application
FROM maven:3.9.7-eclipse-temurin-21-jammy as builder

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file and the source code into the container
COPY pom.xml .
COPY src ./src

# Run the Maven build to create the JAR file
RUN mvn clean package -DskipTests

# Use a smaller JDK image for the final build
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/vacation.jar vacation.jar

# Make port 8080 available to the world outside this container
EXPOSE 9190

# Run the JAR file
ENTRYPOINT ["java", "-jar", "vacation.jar", "--spring.profiles.active=docker"]