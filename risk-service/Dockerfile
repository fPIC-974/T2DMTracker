FROM eclipse-temurin:17-jre-jammy
MAINTAINER FPIC-974
ENV SPING_PROFILES_ACTIVE=docker
COPY target/risk-service-1.0-SNAPSHOT.jar risk-service.jar
EXPOSE 9003
ENTRYPOINT ["java","-jar","/risk-service.jar"]

