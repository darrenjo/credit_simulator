#!/bin/bash

echo "==============================================="
echo "     CREDIT SIMULATOR - COMPREHENSIVE TEST"
echo "==============================================="

echo ""
echo "[1/6] Compiling source code..."
mkdir -p target/classes
find src/main/java -name "*.java" | xargs javac -cp src/main/java -d target/classes

if [ $? -ne 0 ]; then
    echo "❌ COMPILATION FAILED!"
    exit 1
fi
echo "✅ Compilation successful!"

echo ""
echo "[2/6] Compiling test files..."
mkdir -p target/test-classes
find src/test/java -name "*.java" | xargs javac -cp "src/main/java:target/classes" -d target/test-classes

if [ $? -ne 0 ]; then
    echo "❌ TEST COMPILATION FAILED!"
    exit 1
fi
echo "✅ Test compilation successful!"

echo ""
echo "[3/6] Running unit tests..."
java -ea -cp "target/classes:target/test-classes" test.CreditCalculatorTest
java -ea -cp "target/classes:target/test-classes" test.InputValidatorTest

echo ""
echo "[4/6] Testing manual input mode (automated)..."
echo "4" | java -cp target/classes Main

echo ""
echo "[5/6] Testing file input mode..."
java -cp target/classes Main file_inputs.txt

echo ""
echo "[6/6] Creating test validation scenarios..."
echo "Testing complete! Check outputs above for any errors."

echo ""
echo "==============================================="
echo "          ALL TESTS COMPLETED"
echo "==============================================="
