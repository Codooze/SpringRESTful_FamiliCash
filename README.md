# Module 1 Implementing GET
###### (Go to the closest next branch to follow along)
## Table of content
- Controller-Repository Architecture
- Choosing a Database
- Auto-Configuration
- Spring Data's CrudRepository

At this point in our development journey, we’ve got a system which returns a hard-coded Cash Card record from our Controller. However, what we really want is to return real data, from a database. So let’s continue our Steel Thread by switching our attention to the database!

Spring Data works with Spring Boot to make database integration simple. Before we jump in, let’s briefly talk about Spring Data’s architecture.

### Controller-Repository Architecture
The Separation of Concerns principle states that well-designed software should be modular, with each module having distinct and separate concerns from any other module.

Read the rest here:
https://spring.academy/courses/building-a-rest-api-with-spring-boot/lessons/spring-data