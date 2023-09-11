# Implementing DELETE
In this lesson, we’ll implement the last of the four CRUD operations: Delete! By now, you should be familiar with what question we should ask first: What is the API’s data specification for the Delete endpoint? The specification includes the details of the Request and Response.

At the risk of spoiling the outcome, this is the specification that we’ll define:

Request:

- Verb: DELETE
- URI: /cashcards/{id}
- Body: (empty)

Response:

- Status code: 204 NO CONTENT
- Body: (empty)

We’ll return the 204 NO CONTENT status code for a successful delete, but there are additional cases:

<table><thead><tr><th>Response Code</th><th>Use Case</th></tr></thead><tbody><tr><td><code>204 NO CONTENT</code></td><td><ul><li>The record exists, and </li> <li>The Principal is authorized, and </li> <li>The record was successfully deleted. </li> </ul></td></tr><tr><td><code>404 NOT FOUND</code></td><td><ul><li>The record does not exist (a non-existent ID was sent). </li> </ul></td></tr><tr><td><code>404 NOT FOUND</code></td><td><ul><li>The record does exist but the Principal is not the owner. </li> </ul></td></tr></tbody></table>
Why do we return 404 for the "ID does not exist" and "not authorized to access this ID" cases? In order to not "leak" information: If the API returned different results for the two cases, then an unauthorized user would be able to discover specific IDs that they are not authorized to access.

## Hard and Soft Delete
So what does it mean to delete a Cash Card from a database’s point of view? Similar to how we decided that our Update operation means ”replace the entire existing record” (as opposed to supporting partial update), we need to decide what happens to resources when they are deleted.

A simple option, called hard delete, is to delete the record from the database. With a hard delete, it’s gone forever. So what can we do if we need data that existed prior to its deletion?

An alternative is soft delete which works by marking records as "deleted" in the database (so that they are retained, but marked as deleted). For example, we can introduce an IS_DELETED boolean or a DELETED_DATE timestamp column and then set that value-instead of fully removing the record by deleting the database row(s). With a soft delete, we also need to change how Repositories interact with the database. For example, a repository needs to respect the “deleted” column and exclude records marked deleted from Read requests.

continue reading here: https://spring.academy/courses/building-a-rest-api-with-spring-boot/lessons/implementing-delete

### On this lab:

We implement a hard delete endpoint, taking care to follow the pattern of not revealing information about existing records to unauthorized users.

---
## Project Accomplishments

- **RESTful Mastery**: Gained proficiency in HTTP verbs, extending beyond the basic GET to include PUT, PATCH, and POST.

- **HTTP Response Expertise**: Utilized a range of HTTP status codes, such as 200 OK, 204 NO CONTENT, and 201 CREATED, ensuring accurate client-server communication.

- **Spring Boot Proficiency**: Acquired a comprehensive understanding of Spring, including its Inversion of Control container, annotation-driven development, and the power of auto-configuration.

- **Layered Architecture**: Developed a robust API using a layered approach, integrating Spring Security, Spring Web, and Spring Data to manage authentication, HTTP communication, and database interactions respectively.

- **Test-Driven Development (TDD)**: Adopted a test-first methodology, ensuring the application's reliability and functionality.

- **Steel Thread Approach**: Validated the architecture and integration points early in the development process, ensuring a robust foundation for the application.

- **Iterative Improvement**: Consistently applied the Red, Green, Refactor cycle, enhancing both code and tests throughout the development journey.