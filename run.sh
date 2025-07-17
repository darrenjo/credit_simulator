#!/bin/bash
echo "Compiling Credit Simulator..."
mkdir -p target/classes

# Compile all Java files
find src/main/java -name "*.java" | xargs javac -cp src/main/java -d target/classes

if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi

echo "Compilation successful! Starting Credit Simulator..."
java -cp target/classes Main "$@"
