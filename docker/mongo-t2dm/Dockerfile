# Create image for mysql-t2dm
FROM mongo
MAINTAINER FPIC-974

ENV MONGO_INITDB_ROOT_USERNAME root
ENV MONGO_INITDB_ROOT_PASSWORD root
ENV MONGO_INITDB_DATABASE T2DM

ADD doc/db/mongo_import.js /docker-entrypoint-initdb.d

EXPOSE 27017