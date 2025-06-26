#!/bin/bash
mvn clean package -DskipTests
java -jar -Xms512m -Xmx1024m target/holiday-information-service.jar --spring.profiles.active=prod
