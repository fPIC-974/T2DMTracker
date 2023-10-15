FROM eclipse-temurin:17-jre-jammy
MAINTAINER FPIC-974
ENV SPING_PROFILES_ACTIVE=docker
COPY target/note-service-1.0-SNAPSHOT.jar note-service.jar
EXPOSE 9002
ENTRYPOINT ["java","-jar","/note-service.jar"]

