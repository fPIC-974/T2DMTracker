# Create image for mysql-t2dm
FROM mysql
MAINTAINER FPIC-974

ENV MYSQL_ROOT_PASSWORD root
ADD doc/db/mysql_import.sql /docker-entrypoint-initdb.d

EXPOSE 3306