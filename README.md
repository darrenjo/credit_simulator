# Credit Simulator CLI

A Java console application for simulating vehicle loan payments (Car/Motorcycle) with comprehensive validation, file input, API integration, and CLI commands. Built with Maven and ready for Docker deployment.

## 🚀 How to Run

### Prerequisites
- Java 24 or higher
- Maven 3.9+ (for building with Maven)
- Docker (optional, for containerized deployment)

### Quick Start (Windows)
```cmd
run.bat
```

### Quick Start (Linux/macOS)
```bash
chmod +x run.sh
./run.sh
```

### With Maven (Recommended)
```bash
# Compile and package
mvn clean compile package

# Run the application
java -jar target/credit-simulator.jar

# Run with file input
java -jar target/credit-simulator.jar file_inputs.txt
```

### Manual Compilation (Without Maven)
```bash
# Create output directory
mkdir -p target/classes

# Compile all Java files
javac -cp src/main/java -d target/classes $(find src/main/java -name "*.java")

# Run the application
java -cp target/classes Main

# Run with file input
java -cp target/classes Main file_inputs.txt
```

### Input from JSON File
Content example (`file_inputs.txt`):
```json
{
  "vehicleType": "Motor",
  "vehicleCondition": "Baru",
  "vehicleYear": 2025,
  "totalLoanAmount": 150000000,
  "loanTenor": 4,
  "downPayment": 60000000
}
```

## 🧪 Run Unit Tests

### With Maven:
```bash
mvn test
```

### Manual Testing:
```bash
# Compile tests
javac -cp src/main/java:target/classes -d target/test-classes $(find src/test/java -name "*.java")

# Run tests
java -ea -cp target/classes:target/test-classes test.CreditCalculatorTest
java -ea -cp target/classes:target/test-classes test.InputValidatorTest
```

## 🐋 Docker

### Build and run with Docker:
```bash
# Build the image
docker build -t credit-simulator . --no-cache

# Run interactively
docker run -it credit-simulator

# Run with file input
docker run -it credit-simulator java -jar credit-simulator.jar file_inputs.txt
```

## 🔧 Validation Rules

- **Tenor**: 1-6 years only
- **DP for New Vehicle**: ≥ 35% of total loan
- **DP for Used Vehicle**: ≥ 25% of total loan  
- **Vehicle Year**: New vehicles cannot be older than current year - 1
- **Interest Rates**: 
  - Car: 8% base rate
  - Motorcycle: 9% base rate
  - Yearly increases: 0.1% (odd years), 0.5% (even years)

## 📁 Project Structure (Maven Standard)

```
credit_simulator/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Main.java
│   │   │   ├── models/
│   │   │   │   ├── Vehicle.java
│   │   │   │   ├── Car.java
│   │   │   │   ├── Motorcycle.java
│   │   │   │   └── SimulationResult.java
│   │   │   ├── services/
│   │   │   │   ├── CreditCalculator.java
│   │   │   │   ├── VehicleFactory.java
│   │   │   │   ├── ApiService.java
│   │   │   │   ├── CommandHandler.java
│   │   │   │   └── SimulationState.java
│   │   │   └── utils/
│   │   │       ├── InputValidator.java
│   │   │       └── JsonParser.java
│   │   └── resources/
│   │       └── file_inputs.txt
│   └── test/
│       └── java/
│           └── test/
│               ├── CreditCalculatorTest.java
│               ├── InputValidatorTest.java
│               └── MobilCalculationTest.java
├── target/                  # Maven build output (auto-generated)
├── sheets/                  # Auto-created for saved results
├── pom.xml                  # Maven configuration
├── Dockerfile               # Docker configuration
├── .gitlab-ci.yml          # GitLab CI/CD configuration
├── run.bat                  # Windows run script
├── run.sh                   # Linux/macOS run script
├── file_inputs.txt          # Sample input file
└── README.md               # This file
```

## ⚙️ CI/CD Pipeline

The project includes GitLab CI configuration that automatically:
1. **Compiles** the code using Maven
2. **Runs unit tests** with JUnit
3. **Packages** into executable JAR
4. **Builds Docker image** and pushes to Docker Hub (when configured)

## 🖥️ Platform Compatibility

- ✅ **Linux**: Native support with shell scripts
- ✅ **macOS**: Compatible with Unix-based commands  
- ✅ **Windows**: Batch file provided for easy execution
- ✅ **Docker**: Containerized deployment for any platform