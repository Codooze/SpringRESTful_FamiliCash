# Implementing POST
 Our REST API can now fetch Cash Cards with a specific ID. In this lesson, you’ll add the Create endpoint to the API.

Four questions we’ll need to answer while doing this are:

Who specifies the ID - the client, or the server?
In the API Request, how do we represent the object to be created?
Which HTTP method should we use in the Request?
What does the API send as a Response?

Continue reading here: https://spring.academy/courses/building-a-rest-api-with-spring-boot/lessons/implementing-post

## Summary

We’ve made some decisions about how our API will support creating Family Cash Cards:

The API will accept POST requests to create a Cash Card.
The server will create IDs for all Cash Cards.
On success, the API will return a Response with Status Code: 201 CREATED, containing the URI (location) of the new Cash Card resource in the Response headers.
In the lab, we’ll implement these decisions!

## 1 Lab 1: Implementing POST

In this lab you learned how simple it is to add another endpoint to our API -- the POST endpoint. You also learned how to to use that endpoint to create and save a new CashCard to our database using Spring Data. Not only that, but the endpoint accurately implements the HTTP POST specification, which we verified using test driven development. The API is starting to be useful!

---
**Learning Moment:** The guide introduces a learning moment where it challenges the assumption that the database (via the Repository) will manage creating all database id values.
It poses a question: What would happen if an id is provided for a new, unsaved CashCard?
To find out, the guide instructs you to modify the test to provide an id (44L) for the CashCard and observe the outcome.
The test fails with a 500 INTERNAL_SERVER_ERROR. The reason is that the Repository is trying to find a CashCard with an id of 44 and throws an error when it cannot find it.
The guide explains that supplying an id to cashCardRepository.save is supported when updating an existing resource, not when creating a new one.
The main takeaway from this learning moment is that the API requires not supplying a CashCard.id when creating a new CashCard.