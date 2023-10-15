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
This is a *MongoDB* docker container hosting the database used by **note-service**.

Dockerfile can be found [here](docker/mongo-t2dm/Dockerfile).

To build the image :
```
> pwd
~/T2DMTracker
> cd docker/mongo-t2dm
> docker build -t mongo-t2dm .
```

To run the image (for testing purpose only, images will be loaded through docker-compose) :
```
> docker run --detach --name=mongo-t2dm --publish 27017:27017 mongo-t2dm:latest
```

#### patient-service
#### note-service
#### risk-service
#### web-service
#### gateway-service
