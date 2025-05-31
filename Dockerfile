# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Set environment variable
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application with port binding
CMD ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar target/ExpenseTracker-0.0.1-SNAPSHOT.jar"]
