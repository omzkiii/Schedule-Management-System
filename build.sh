#!/bin/bash

javac Controllers.java Main.java Queries.java
java -classpath ".:sqlite-jdbc-3.7.2.jar" Main
