# T2DM Tracker
## Introduction
The goal of the project is to provide an application to handle Tier 2 Diabetes patients.

## Project information
### Running the project
#### Setup
This project assumes *Docker* is installed on the system.

You must **build** docker images for each of the following services :
- [`docker/mongo-t2dm`](#mongo-t2dm)
- [`docker/mysql-t2dm`](#mysql-t2dm)
- [`patient-service`](#patient-service)
- [`note-service`](#note-service)
- [`risk-service`](#risk-service)
- [`web-service`](#web-service)
- [`gateway-service`](#gateway-service)

Then you can execute the following commands to start the containers :
```
> pwd
~/T2DMTracker
> docker compose up -d
> docker container ps
CONTAINER ID   IMAGE                    COMMAND                  CREATED         STATUS         PORTS                    NAMES
62fa4246585c   patient-service:latest   "java -jar /patient-…"   6 seconds ago   Up 3 seconds   9001/tcp                 t2dmtracker-patient-service-1
9bedb1ce1fc5   note-service:latest      "java -jar /note-ser…"   6 seconds ago   Up 3 seconds   9002/tcp                 t2dmtracker-note-service-1
dca0e37ea3bc   gateway-service:latest   "java -jar /gateway-…"   6 seconds ago   Up 4 seconds   0.0.0.0:8080->8080/tcp   t2dmtracker-gateway-service-1
56f637f24d30   web-service:latest       "java -jar /web-serv…"   6 seconds ago   Up 4 seconds   9003/tcp                 t2dmtracker-web-service-1
94e0be7e45ab   mongo-t2dm:latest        "docker-entrypoint.s…"   6 seconds ago   Up 4 seconds   27017/tcp                t2dmtracker-mongo-t2dm-1
11b040d3129d   risk-service:latest      "java -jar /risk-ser…"   6 seconds ago   Up 4 seconds   9003/tcp                 t2dmtracker-risk-service-1
da5f72b38aae   mysql-t2dm:latest        "docker-entrypoint.s…"   6 seconds ago   Up 4 seconds   3306/tcp, 33060/tcp      t2dmtracker-mysql-t2dm-1
```

To stop :
```
> pwd
~/T2DMTracker
> docker compose down
> docker container ps
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES
```

#### Accessing the application
The only port exposed to your local machine is *8080*.

Open a browser and access the following *URL* :
>http://localhost:8080

Credentials : `admin:admin`

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

To run the image :
```
docker run --detach --name=mysql-t2dm --publish 3306:3306 mysql-t2dm:latest
```

> :warning: This command will run the image locally, and expose port 3306 to access the database.

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

To run the image :
```
docker run --detach --name=mongo-t2dm --publish 27017:27017 mongo-t2dm:latest
```
> :warning: This command will run the image locally, and expose port 27017 to access the database.

#### patient-service
This API handles core Patient management features.

Dockerfile can be found [here](patient-service/Dockerfile).

To build the module and the docker image :
```
> pwd
~/T2DMTracker/patient-service
> mvn clean package
> docker build -t patient-service .
```

To run the image :
```
docker run --detach --name=patient-service --publish 9001:9001 patient-service:latest
```
> :warning: This command will run the image locally, and expose port 9001 to access the application.

###### Endpoints
```
   [GET] /api/patient/list
   [GET] /api/patient
   [PUT] /api/patient
  [POST] /api/patient
[DELETE] /api/patient
```
#### note-service
This API handles core Note management features.

Dockerfile can be found [here](note-service/Dockerfile).

To build the module and the docker image :
```
> pwd
~/T2DMTracker/note-service
> mvn clean package
> docker build -t note-service .
```

To run the image :
```
docker run --detach --name=note-service --publish 9002:9002 note-service:latest
```
> :warning: This command will run the image locally, and expose port 9002 to access the application.

###### Endpoints
```
   [GET] /api/note
  [POST] /api/note
[DELETE] /api/note
```
#### risk-service
This API handles core Risk assessment features.

Dockerfile can be found [here](risk-service/Dockerfile).

To build the module and the docker image :
```
> pwd
~/T2DMTracker/risk-service
> mvn clean package
> docker build -t risk-service .
```

To run the image :
```
docker run --detach --name=risk-service --publish 9003:9003 risk-service:latest
```
> :warning: This command will run the image locally, and expose port 9003 to access the application.

###### Endpoints
```
   [GET] /api/risk
```
#### web-service
This Web service handles the main application.

Dockerfile can be found [here](web-service/Dockerfile).

To build the module and the docker image :
```
> pwd
~/T2DMTracker/web-service
> mvn clean package
> docker build -t web-service .
```

To run the image :
```
docker run --detach --name=web-service --publish 9000:9000 web-service:latest
```
> :warning: This command will run the image locally, and expose port 9000 to access the application.

#### gateway-service
This Gateway service handles the routing rules inside the application.

Dockerfile can be found [here](gateway-service/Dockerfile).

To build the module and the docker image :
```
> pwd
~/T2DMTracker/gateway-service
> mvn clean package
> docker build -t gateway-service .
```

To run the image :
```
docker run --detach --name=gateway-service --publish 8080:8080 gateway-service:latest
```
> :warning: This command will run the image locally, and expose port 8080 to access the application.
