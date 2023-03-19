# Welcome


TODO describe application



## Spring Boot

- [Spring boot doc](https://docs.spring.io/spring-boot/docs/3.0.4/reference/html/)
- [How to guide](https://docs.spring.io/spring-boot/docs/3.0.4/reference/html/howto.html#howto)


## Spring Web MVC

Annotations:

- @Controller
- @RestController (@Controller U @ReponseBody)
- @ResponseBody
- @RequestBody
- @RequestMapping
- @GetMapping
- @PostMapping
- @PutMapping
- @DeleteMapping
- @RequestParam
- @PathVariable


## HTTP

There exists four types of request:

- GET: retrieves information from the server
- POST: submits data to the server
- PUT: updates an existing resource on the server
- DELETE: deletes a resource from the server

Moreover, we have a set of status Code:

- 1xx: informational response
- 2xx: successful
- 3xx: redirection
- 4xx: client error
- 5xx: server error

To gain more knowledge on HTTP status code and have a complete list, read the [List of HTTP status codes](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes) Wikipedia page.


## N Tier Architecture

- Client
- API Layer
- Business Layer
- DAO Layer
- Database

TODO: Diagram

## Dependencies







## Embedded Web Server


Each Spring Boot application contains an embedded Tomcat web server

### Configure Server

`application.yaml` file contains all the confifuration of the web server

TODO: application type: servlet vs reactive


### Docker

#### Install Docker

#### Docker Compose

### Postgres

##### Setup Database

enter the database: `docker exec -it postgres bash`

enter the interactive shell client: `psql -U username`

create database: `CREATE DATABASE database_name`

select database: `\c database_name`

show relations (schemas): `\d`, `\dn` or `\d table_name`


#### Spring Data JPA

- `findAll()`: returns all records of an entity
- `findById(id)`: returns a record by its primary key
- `save(entity)`: saves a new record or updates an existing record
- `deleteById(id)`: deletes a record by its primary key
- `existsById(id)`: checks if a record exists by its primary key
- `count()`: returns the count of records for an entity
- `findAll(Sort sort)`: returns all records of an entity, sorted by the specified attribute
- `findAll(Pageable pageable)`: returns a page of records, according to the specified paging and sorting options
- `findBy[Attribute](value)`: returns a list of records by the specified attribute value
- `findFirstBy[Attribute](value)`: returns the first record found by the specified attribute value
- `findTopBy[Attribute](value)`: returns the first record found by the specified attribute value
- `findBy[Attribute]Containing(value)`: returns a list of records where the specified attribute contains the specified value

##### Install JDBC PostgresSQL drivers

```pom.xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

#### Flyway (Database migration)

Manage database relations. It used to version and automate database deployments.

- Documentation: [https://documentation.red-gate.com/fd/flyway-cli-and-api-183306238.html](https://documentation.red-gate.com/fd/flyway-cli-and-api-183306238.html)
### Documentation

Spring Openapi (swagger): [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)