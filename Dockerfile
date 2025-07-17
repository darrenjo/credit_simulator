\bca\credit_simulator\Dockerfile
FROM maven:3.9-openjdk-24 AS build

# Set working directory
WORKDIR /app

# Copy Maven files
COPY pom.xml .
COPY src/ ./src/
COPY file_inputs.txt ./

# Build the application
RUN mvn clean compile package -DskipTests

# Runtime stage
FROM openjdk:24-jre-slim

WORKDIR /app

# Copy the built JAR and input files
COPY --from=build /app/target/credit-simulator.jar ./credit-simulator.jar
COPY --from=build /app/file_inputs.txt ./

# Create a simple run script
RUN echo '#!/bin/bash\njava -jar credit-simulator.jar "$@"' > /app/run.sh && \
    chmod +x /app/run.sh

# Default command
CMD ["java", "-jar", "credit-simulator.jar"]