## Developer note:
- This project developed with Java 11 and spring framework(spring boot v 2.6.4)
- Mongodb used for store data. save entities, persist request and response etc.
    - Mongo express is used and runs in 8081 port.
    - Mongodb credentials
       ```
       - username: getir
       - password: getir
       - database: readingisgood
      ```
- Rest APIs documented with swagger on [Swagger](http://localhost:8080/swagger-ui)
- APIs serving on 8080 port.
- Project uses spring token based security for authentication. Token must be taken in given endpoint with username and password credentials.
     ```
     - endpoint: /api/auth/login
     - username: getir
     - password: getir
    ```
- Api-docs of rest client(ex: postman) can be reachable on ```${project.base}/api-docs```
- Postman request collections are added on ```${project.base}/getir.postman_collection.json```
- Integration and unit tests exist for service and controllers and run with mock services.
- Logging is available
- API urls:
    - ```POST /api/auth/login``` to authenticate for jwt token
    - ```POST /api/customers``` to create customer
    - ```GET /api/customers``` to list customers
    - ```POST /api/books``` to create book
    - ```PUT /api/book/update-stock``` to update stock of existing book
    - ```POST /api/orders``` to create order
    - ```GET /api/orders/{id}``` to get order with id
    - ```GET /api/orders``` to list order with given start date and end date
    - ```GET /api/statistics/monthly``` to give monthly statistics

## Tech stack:

* Java 11
* Mongodb
* Docker
* Swagger
* Mockito

## Design
- Db Design
    - Customer has name, surname and email. Email field is unique for customers.
    - Book has name, price and stock.
    - CustomerOrder is table for order. CustomerOrder has ordered date, price, status, customer and book list. Book list keeps the book and quantity list of orders.
    
- System Design
    - The project is started with designing database.
    - Controllers and services and designed.
    - Tests are written.

- Validations
    - Email is unique for customers. Email can not be null
    - Book name and price can not be null.
    - For orders customer and book must be existed.
    - Customers can not be order book which its stock is not enough.
    - Customer can not be order book with negative quantity.
    - Customer can not be buy same book with same time.

- Assumptions
    - It is assumed that there is no data in the system at first.
    - It is developed for distributed operation (docker)

## Build

#Running steps:
- Go to project directory.
- Build project and create projects docker image.
    - ```./mvnw clean install```
- Build a docker image with desired tag
    - ```docker build -f .\docker\Dockerfile -t readingisgood:{tag} .```
- Run docker image with desired port
    - ```docker run -p {port}:8080 readingisgood:{tag}```

**zkaratatar**
