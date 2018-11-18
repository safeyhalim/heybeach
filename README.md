# Heybeach

Heybeach is a service that allows users to upload and download beach photos. In its minimum viable product (MVP), the service doesn't check the photos content therefore all kinds of photos can be uploaded. But in future releases, the service will analyze the uploaded photos and make sure the the photo contains a beach. The service is written in Java and it exposes a REST API [Spring Boot](https://projects.spring.io/spring-boot/) technology.

The minimum viable product (MVP) is currently deployed on Cloud Foundry and can be access via the URL: https://heybeach.cfapps.io/
The section below describes how to use the API.

## REST API
The Heybeach service offers 5 API endpoints. The user registration and the login endpoints are publicly accessible, while the remaining endpoints are restricted. Access to the upload/list/download endpoints is only available via a bearer token which is obtained after a successful login.  

| HTTP Method | Mapping | Parameters | Request type | Authentication |Description |
| --- |--- | --- | --- | --- | --- |
| POST | /heybeach/welcome/register | email, password, repeatedPassword | JSON | N/A |Registers a new user. email: must have a valid email format xxx@yyy.zz password and repeatedPassword must be identical  |
| POST | /oauth/token | username, password, grant_type | Form (URL-encoded) | Basic Authentication: username: "heybeachapiclientid", password: "Ob747dilVK" | User login. username should be set to the registered user's email, password should be set the registered user password. grant_type must be set to the value 'password' |
| POST | /heybeach/home/upload | photo | Multipart Form | Bearer token |Uploads a photo for the authenticated user. the parameter photo is set to the photo's file. Accepted content types: image/jpeg and image/png |
| GET | /heybeach/home/list | N/A | N/A | Bearer token | Lists all photo's info for the authentication user. The response is a JSON array. Each elements contain a photo's info: id, name, type |
|GET | /heybeach/home/download | id | N/A | Bearer token | Downloads one photo that was uploaded by the authenticated user identified by id (that can be fetched by a /list request) |
## Installation and Local testing
1. Clone the repository
2. You can import the project in your preferred IDE, but it's recommended for Eclipse users to use [Spring Tool Suite (STS)](https://spring.io/tools)
3. Install [MySQL](https://dev.mysql.com/downloads) Locally. Heybeach is configured to use a database called heybeachdb available on a MySQL server deployed on localhost, listeing to port: 3306 and accessed with username:root and password: root. You can choose a different database technoloy and a different endpoint and/or credentials without affecting the service implementation. Just make sure you modifiy the configuration file (application.properties accordingly)
4. Perform a gradle build (On STS, do a gradle refresh)
5. Start the Spring boot application. The service should run under the base endpoint: localhost:8080/

## Architectural Decisions and Product Roadmap
Check the wiki
