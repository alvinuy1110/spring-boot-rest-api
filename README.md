# Spring Boot REST API

This is to demonstrate Spring Boot API.

### Requirements

* JDK 1.8+
* Spring Boot 1.3.2


### Features

##### Spring Hateoas for Hypermedia support
##### Spring Data JPA for Repositories

##### Spring Actuator

	/info - for application info
	/health - for health monitoring
	/metrics - jmx style info
	/env - environment properties

##### Pagination Support

Parameters:
* page - page number starting with 0
* size - number of elements to return		

##### Sorting Support

Parameters:
* sort - sort a property

Order:
* asc - ascending (default)
* desc - descending		

Syntax:

	?sort=<property_name>[,(asc|desc)]

Example:

	?sort=firstname&sort=lastname,asc



##### Embedded database
Currently using H2 embedded to store data.  SQL files are found under "src/main/resources/sql".


##### Swagger 2.0

	/swagger-resources - get the resources