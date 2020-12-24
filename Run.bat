@echo off

Rem Would have to change the path for yourself... this is where my Javafx is...
SET path="C:\Program Files\Java\javafx-sdk-15.0.1\lib"

"%JAVA_HOME%\bin\javac" --module-path %path% --add-modules javafx.controls Waterjug.java

"%JAVA_HOME%\bin\java" --module-path %path% --add-modules javafx.controls Waterjug.java
