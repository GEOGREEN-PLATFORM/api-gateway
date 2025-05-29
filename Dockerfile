FROM openjdk:21-slim
LABEL authors="GEOGREEN-PLATFORM"
COPY target/api-gateway*.jar /api-gateway.jar
ENTRYPOINT ["java", "-jar", "/api-gateway.jar"]