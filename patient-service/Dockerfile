FROM eclipse-temurin:17-jre-jammy
MAINTAINER FPIC-974
ENV SPING_PROFILES_ACTIVE=docker
COPY target/patient-service-1.0-SNAPSHOT.jar patient-service.jar
EXPOSE 9001
ENTRYPOINT ["java","-jar","/patient-service.jar"]

