# REST APIs for URL Shortening

## Frameworks and Tools used
- Spring Boot
- Swagger

## How to run
1. Clone the repo: ```git clone https://github.com/thecoducer/url-shortener-api.git```
2. Create a database in MYSQL Workbench: ```create database shortenurl_db;```
3. Open the project in IntelliJ or Eclipse. Set configurations in IDE to run a Maven project.
4. Edit the **application.properties** file and set your own MYSQL username and password.
5. And now run the application.
```
# Connection to Database
spring.datasource.url= jdbc:mysql://localhost:3306/shortenurl_db
spring.datasource.username= <your username here>
spring.datasource.password= <your password here>
```
