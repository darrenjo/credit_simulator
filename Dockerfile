FROM maven:3.9.10-amazoncorretto-24 AS build

WORKDIR /app

COPY pom.xml .
COPY src/ ./src/
COPY file_inputs.txt ./

RUN mvn clean compile package -DskipTests

FROM amazoncorretto:24

WORKDIR /app

COPY --from=build /app/target/credit-simulator.jar ./credit-simulator.jar
COPY --from=build /app/file_inputs.txt ./

CMD ["java", "-jar", "credit-simulator.jar"]