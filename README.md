# Module 1 Repositories & Spring Data
###### (Go to the closest next branch to follow along)
## Table of content
- Controller-Repository Architecture
- Choosing a Database
- Auto-Configuration
- Spring Data's CrudRepository

## Summary
You've now successfully refactored the way the Family Cash Card API manages its data. Spring Data is now creating an in-memory H2 database and loading it with test data, which our tests utilize to exercise our API.

Furthermore, we didn't change any of our tests! They actually guided us to a correct implementation. How awesome is that?!

---
At this point in our development journey, we’ve got a system which returns a hard-coded Cash Card record from our Controller. However, what we really want is to return real data, from a database. So let’s continue our Steel Thread by switching our attention to the database!

Spring Data works with Spring Boot to make database integration simple. Before we jump in, let’s briefly talk about Spring Data’s architecture.

### Controller-Repository Architecture
The Separation of Concerns principle states that well-designed software should be modular, with each module having distinct and separate concerns from any other module.

Read the rest here:
https://spring.academy/courses/building-a-rest-api-with-spring-boot/lessons/spring-data

## Additional Notes:

#### Understanding the Dependencies:

Spring Data JDBC:
Spring Data provides various implementations for different types of databases (both relational and non-relational).
These implementations are often referred to as Object-Relational Mapping (ORM) frameworks. ORM is a technique that lets you interact with your database using an object-oriented paradigm.
The chosen implementation here is Spring Data JDBC. It's described as a simple, limited, and opinionated ORM.
H2 Database:
H2 is a fast, open-source SQL database written in Java.
It's compatible with Spring Data JDBC, making it a good choice for this project.
The testImplementation scope means that the H2 database will only be available during test execution. This is useful for isolating test environments from production or development environments.
Running Tests:

After adding the dependencies, you're instructed to run tests using the ./gradlew test command.
This serves two purposes:
It installs the newly added dependencies.
It verifies that the new dependencies haven't introduced any issues.
The mention of "Shutting down embedded database" indicates that Spring's Auto Configuration feature is automatically setting up and tearing down an H2 database for testing purposes.

---

>  Understanding the Code:

**Configuring the  `CashCardRepository`:**

You need to modify the CashCardRepository to indicate that it manages data for the CashCard and that the data type of the Cash Card's ID is Long.
This is done by changing the declaration to public interface CashCardRepository extends CrudRepository<CashCard, Long> {}.
Configuring the CashCard:

By setting the repository as CrudRepository<CashCard, Long>, you've indicated that the ID of CashCard is of type Long. But, Spring Data still needs to know which field in the CashCard class represents this ID.
You're instructed to edit the CashCard class and annotate the id field with @Id to specify it as the identifier for the CashCardRepository.
Additionally, you need to import the @Id annotation from org.springframework.data.annotation.Id.

---

### Configure the Database for Testing with Spring Data and H2

**Issue:** After replacing hard-coded CashCard data with cashCardRepository.findById, tests crash due to a missing database table named CASH_CARD.

**Solution:**

**Database Schema Configuration:**
Use src/test/resources/schema.sql to automatically configure a test database.
Un-comment the provided SQL in schema.sql to create the CASH_CARD table with columns ID and AMOUNT.
Understanding: A database schema is like a blueprint for data storage. The schema here mirrors the CashCard object with an id and an amount.

**Loading Test Data:**
Spring Data can also preload data for testing.
Un-comment the SQL in src/test/resources/data.sql to insert a CashCard entry with ID=99 and AMOUNT=123.45.

**Test Outcome:** After these configurations, tests pass successfully without errors.