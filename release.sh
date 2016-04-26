#!/usr/bin/env bash
rm pom.xml.releaseBackup
rm release.properties
mvn clean
mvn install
mvn release:prepare