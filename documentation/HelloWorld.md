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

## Dependencies







## Embedded Web Server


Each Spring Boot application contains an embedded Tomcat web server

### Configure Server

`application.yaml` file contains all the confifuration of the web server

TODO: application type: servlet vs reactive