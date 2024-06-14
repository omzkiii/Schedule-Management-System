#!/bin/bash

javac Main.java 
javac ./controller/Controllers.java ./controller/Queries.java
javac ./model/Course.java
javac ./model/Faculty.java
javac ./model/Schedule.java
java -classpath ".:sqlite-jdbc-3.7.2.jar" Main
