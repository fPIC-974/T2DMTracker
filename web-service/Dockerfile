FROM eclipse-temurin:17-jre-jammy
MAINTAINER FPIC-974
ENV SPING_PROFILES_ACTIVE=docker
COPY target/web-service-1.0-SNAPSHOT.jar web-service.jar
EXPOSE 9003
ENTRYPOINT ["java","-jar","/web-service.jar"]

