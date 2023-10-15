FROM eclipse-temurin:17-jre-jammy
MAINTAINER FPIC-974
ENV SPING_PROFILES_ACTIVE=docker
COPY target/gateway-service-1.0-SNAPSHOT.jar gateway-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/gateway-service.jar"]
