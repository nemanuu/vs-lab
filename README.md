[![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg)](http://opensource.org/licenses/MIT)
[![Travis Build Status](https://travis-ci.org/mavogel/hska-vis-legacy.svg?branch=master)](https://travis-ci.org/mavogel/hska-vis-legacy)

# Distributed Information Systems Laboratory aka vis-lab
This project is the quick setup of the legacy webshop of 
the masters course 'Distributed Information Systems' at the University of Applied Sciences (Karlsruhe).


## Structure of the project
There are five folders:
- [category](./category) for the Spring **Category Microservice**
- [product](./product) for the Spring **Product Microservice**
- [database](./database) for everthing related to the **database**
- [webshop](./webshop) for the **UI** and **user management** part of the webshop
- [docker](./docker) for everthing related to the **docker** setup (-> the dockerfiles)


### Spring projects
Both Category and Product microservices use Spring with Version **2.2.6** and **Java 17**. The inital structure was created with [Spring Intializr](https://start.spring.io/).


To use Spring you need Java and Maven.
Install Maven by following the instructions [here](https://maven.apache.org/guides/getting-started/windows-prerequisites.html) and [here](https://maven.apache.org/install.html).


#### Starting the product microservice without docker
Use these commands if you want to test if the product service runs independantly.
Start the application from you preferred IDE or execute in the [product](./product) folder
```bash
./mvnw spring-boot:run
```

Test the application by opening
```
http://localhost:8081/products
```
Get a product via an id (Replace 42 with a valid id)
```
http://localhost:8081/product?id=42
```


# <a name="quick-start"></a>Quick Start (docker-hub)

- Copy the `docker-compose.yml` locally in a desired folder and run
```bash
$ docker-compose up -d
# to follow the logs
$ docker-compose logs -tf
# to shutdown
$ docker-compose down
```

Docker needs a jar for the product-service. Currently, I'm not sure if/how docker can do this on its own.
Please test if `docker-compose -f docker-compose-local.yml up -d` works to start all containers.
If docker can not find the jar for the product service, run the following in the [product](./product) folder to generate a jar in the product/target folder.
```bash
mvn clean package
```
Then update this readme.


### <a name="built-it-on-your-own"></a>Built it on your own
- Run `docker-compose -f docker-compose-local.yml up -d` which will
    - It builds the web app `war` in a staged build, packs it into a docker tomcat8 container,
    and sets the user `tomcat` with password `admin` for the Management Console at http://localhost:8888/
    - Initializes the MySQL Database docker container with the db user defined in `hibernate.cfg.xml`
    - Sets up both containers and make the legacy webshop available under http://localhost:8888/EShop-1.0.0
- Follow the logs via `docker-compose -f docker-compose-local.yml logs -tf`
- To shutdown the containers run `docker-compose -f docker-compose-local.yml down`

## <a name="database-cleanup"></a>Database Cleanup
If you change the user and password of the MySQL database, you should run
```bash
$ docker-compose rm -v
$ rm -rf .data
```
Details can be found [here](https://github.com/docker-library/mysql/issues/51)

## <a name="license"></a>License
Copyright (c) 2017-2018 Manuel Vogel
Source code is open source and released under the MIT license.
