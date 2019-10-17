#!/bin/bash
mvn clean compile
mvn exec:java -Dexec.executable="applicationdomain.Application" -Dlog4j.configuration="file:log4j.properties"
