# Spring Microservices

> Example of a microservice architecture using Spring Cloud

## Overview

The architecture is composed by five services:

- `eureka-service`: Service Discovery Server created with **Eureka**
- `zoul-services`: API Gateway created with **Zuul** that uses the `discovery-service` to send the requests to the services.
- `auth-service`: Simple service created with **Spring Boot** to use as authetication and authroziation using Oauth2
- `university-service`: Simple REST service created with **Spring Boot** to use as an example
- `student-service`: Simple REST service created with **Spring Boot** to use as an example

The services: `zoul-services` are already configured with **Hystrix (latency and fault tolerance library)** and are providing a **stream** that you can use to monitor with a **Hystrix/Turbine** dashboard. You can check the **Hystrix Stream** accessing the service URL with `/hystrix.stream` (example: `http://localhost:8765/hystrix.stream`)

## How to use

To test this architecture you will need to have: **JDK 8+**, **Docker** and **Maven** installed

- Clone this repo and enter it

- Run the `start.sh` script, it will use **Maven** to build the `.jar` file for each service and then use Docker to build the containers with the `.jar` files

In the default configuration you will have:

- **eureka-service** running on port `8761`, access `http://localhost:8761` to see the dashboard
- **zoul-services** running on port `8765`, you will send the requests to this service
- **university-service** running on ports: `8083` 
- **student-service** running on ports: `8082` 

After running the containers, head to `http://localhost:8761` to make sure that the four services (two **article** and two **author**) are registered in the **Discovery Service**, when they're all registered you can test the application with `curl` making requests to the endpoints below:

- `curl http://localhost:8765/gateway-2/uni/students`
- `curl http://localhost:8765/gateway-5/student/1`
- `curl http://localhost:8765/gateway-2/`

for calling these API you need token that you can generate from below API

POST --- http://localhost:8081/oauth/token   with x-www-form-urlencoded'

with below param:

username-xxxxxxxxx@gmail.com
password--- xxxxxx
grant_type-- password
client_id --- webapp
client_secret--- websecret
scope--- read/write


To create User:

API---http://localhost:8081/save

Request Body:

{
	"firstname":"abc",
	"lastname":"xyz",
	"mobilenumber":"9022967255",
	"email":"xxxxxxx@gmail.com",
	"gender":"MALE",
	"password":"xxxx5007",
	"dob":"11/11/1992",
	"roleses":[{
		"name":"ROLE_USER"
	}]
}


You can find users.sql file for schema Import Table in Mysql Databases:

Create database First >create database users
Import sql file >source C:\Users\kunal.lawand\Desktop\test.sql
