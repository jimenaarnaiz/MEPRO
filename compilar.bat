if not exist "bin" mkdir bin

javac -classpath .\bin;.\lib\* ^
-encoding UTF-8 ^
-d bin ^
-sourcepath .\src ^
.\src\quantik\control\*.java ^
.\src\quantik\excepcion\*.java ^
.\src\quantik\modelo\*.java ^
.\src\quantik\textui\*.java ^
.\src\quantik\undo\*.java ^
.\src\quantik\util\*.java ^
