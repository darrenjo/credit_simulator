@echo off
echo Compiling Credit Simulator...
if not exist target\classes mkdir target\classes

for /r src\main\java %%i in (*.java) do (
    javac -cp src\main\java -d target\classes "%%i"
)

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful! Starting Credit Simulator...
java -cp target\classes Main %*
pause
