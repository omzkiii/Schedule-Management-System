#!/bin/bash

javac --module-path "./javafx-sdk-22.0.1/lib" --add-modules javafx.controls,javafx.fxml Main.java 
javac ./controller/Controllers.java ./controller/Queries.java
javac ./model/Course.java
javac ./model/Faculty.java
javac ./model/Schedule.java
java --module-path "./javafx-sdk-22.0.1/lib" --add-modules javafx.controls,javafx.fxml -classpath ".:sqlite-jdbc-3.7.2.jar" Main
