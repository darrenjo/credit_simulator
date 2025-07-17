@echo off
echo ===============================================
echo      CREDIT SIMULATOR - COMPREHENSIVE TEST
echo ===============================================

echo.
echo [1/6] Compiling source code...
if not exist target\classes mkdir target\classes
javac -cp src\main\java -d target\classes src\main\java\*.java src\main\java\models\*.java src\main\java\services\*.java src\main\java\utils\*.java

if %errorlevel% neq 0 (
    echo ❌ COMPILATION FAILED!
    pause
    exit /b 1
)
echo ✅ Compilation successful!

echo.
echo [2/6] Compiling test files...
if not exist target\test-classes mkdir target\test-classes
javac -cp "src\main\java;target\classes" -d target\test-classes src\test\java\test\*.java

if %errorlevel% neq 0 (
    echo ❌ TEST COMPILATION FAILED!
    pause
    exit /b 1
)
echo ✅ Test compilation successful!

echo.
echo [3/6] Running unit tests...
java -ea -cp "target\classes;target\test-classes" test.CreditCalculatorTest
java -ea -cp "target\classes;target\test-classes" test.InputValidatorTest

echo.
echo [4/6] Testing manual input mode (automated)...
echo 4 | java -cp target\classes Main

echo.
echo [5/6] Testing file input mode...
java -cp target\classes Main file_inputs.txt

echo.
echo [6/6] Testing validation rules...
echo Testing complete! Check outputs above for any errors.

echo.
echo ===============================================
echo           ALL TESTS COMPLETED
echo ===============================================
pause
