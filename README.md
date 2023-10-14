# T2DM Tracker
## Introduction
The goal of the project is to provide an application to handle Tier 2 Diabetes patients.

## Project information
### Services
#### mysql-t2dm
This is a *MySQL* docker container hosting the database used by **patient-service**.

Dockerfile can be found [here](docker/mysql-t2dm/Dockerfile).

Database initialization script is injected in image, and can be found [here](docker/mysql-t2dm/doc/db/mysql_import.sql).

To build the image :
```
> pwd
~/T2DMTracker
> cd docker/mysql-t2dm
> docker build -t mysql-t2dm .
```

To run the image (for testing purpose only, images will be loaded through docker-compose) :
```
> docker run --detach --name=mysql-t2dm --publish 3306:3306 mysql-t2dm:latest
```
#### mongo-t2dm
#### patient-service
#### note-service
#### risk-service
#### web-service
#### gateway-service
