# REST APIs for URL Shortening

## Frameworks and Tools used
- Spring Boot
- Swagger v2

## How to run
1. Clone the repo: ```git clone https://github.com/thecoducer/url-shortener-api.git```
2. Create a database in MYSQL Workbench: ```create database shortenurl_db;```
3. Open the project in IntelliJ or Eclipse. Set configurations in IDE to run a Maven project.
4. Edit the **application.properties** file and set your own MYSQL username and password.
```
# Connection to Database
spring.datasource.url= jdbc:mysql://localhost:3306/shortenurl_db
spring.datasource.username= <your username here>
spring.datasource.password= <your password here>
```
5. And, now run the application.

## Features
1. Built REST API endpoints like /storeurl, /count, /get and /list. 
2. Used MySQL as database to persist data.
3. Used @ControllerAdvice annotation to build a global exception handler.
4. Have written test classes.
5. Used MD5 hash and Base64 encoder to generate the short key.
6. Integrated Swagger framework for REST API documentation.
7. Paid attention to modular design, and have written readable and maintanable code.

## Application Architecture
![](https://raw.githubusercontent.com/thecoducer/url-shortener-api/main/docs/app-architecture.jpg)

## How short key generation works?
![](https://raw.githubusercontent.com/thecoducer/url-shortener-api/main/docs/shortening-technique.png) <br/>
Using this technique we can generate around 280 Trillion unique short keys.

## Database Design
![](https://raw.githubusercontent.com/thecoducer/url-shortener-api/main/docs/db-design.jpg) <br/>
![](https://raw.githubusercontent.com/thecoducer/url-shortener-api/main/docs/db-schema.jpg)

