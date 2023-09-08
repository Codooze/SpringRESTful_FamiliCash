# Returning a list with GET
Now that our API can create Cash Cards, it’s reasonable to learn how to fetch all (or some!) of the Cash Cards. In this lesson, we’ll implement the “Read Many” endpoint, and understand how this operation differs substantially from the Read endpoint that we previously created.

# Requesting a list of Cash Cards
We can expect each of our Family Cash Card users to have a few cards: maybe one for each dependent, and perhaps a few that were given as gifts. The API should be able to return multiple Cash Cards in response to a single REST request.

When you make an API request for several Cash Cards, you’d ideally make a single request, which returns a list of Cash Cards. So, we’ll need a new data contract. Instead of a single Cash Card, the new contract should specify that the response is a JSON Array of Cash Card objects:
```json
[
  {
    "id": 1,
    "amount": 123.45
  },
  {
    "id": 2,
    "amount": 50.00
  }
]
```

It turns out that our old friend, CrudRepository, has a findAll method that we can use to easily fetch all the Cash Cards in the database. Let's go ahead and use that method. At first glance, it looks quite simple:

```java
@GetMapping()
public ResponseEntity<Iterable<CashCard>> findAll() {
   return ResponseEntity.ok(cashCardRepository.findAll());
}
```

However, it turns out there’s a lot more to this operation than just returning all the Cash Cards in the database. Some questions come to mind:

How do I return only the Cash Cards that the user owns? (Great question! We’ll discuss this in the upcoming Spring Security lesson).
What if there are hundreds (or thousands?!) of Cash Cards? Should the API return an unlimited number of results or return them in “chunks”? This is known as Pagination.
Should the Cash Cards be returned in a particular order (i.e., should they be sorted?)?
We’ll leave the first question for later, but answer the pagination and sorting questions in this lesson. Let’s press on!

read the rest here https://spring.academy/courses/building-a-rest-api-with-spring-boot/lessons/get-list

# lab 2: Returning a list with GET

Both files, `CashCardJsonTest.java` and `CashCardApplicationTests.java`, are test classes written in Java for testing different aspects of the `CashCard` functionality. Let's break down their purposes and the differences between them:

### CashCardJsonTest.java
1. **Purpose**: This class focuses on testing the serialization and deserialization of the `CashCard` object to and from JSON format. It ensures that the JSON representation of the object is correct and that the object can be correctly reconstructed from its JSON representation.
2. **Annotations**:
    - `@JsonTest`: This annotation indicates that the test focuses on JSON serialization and deserialization.
3. **Tests**:
    - `cashCardSerializationTest()`: Tests the serialization of a `CashCard` object to JSON.
    - `cashCardDeserializationTest()`: Tests the deserialization of a JSON string back to a `CashCard` object.
    - `cashCardListSerializationTest()`: Tests the serialization of a list of `CashCard` objects to JSON.
    - `cashCardListDeserializationTest()`: Tests the deserialization of a JSON string back to a list of `CashCard` objects.

### CashCardApplicationTests.java
1. **Purpose**: This class focuses on integration testing of the `CashCard` RESTful API endpoints. It tests the behavior of the API when it receives HTTP requests and ensures that the API responds correctly.
2. **Annotations**:
    - `@SpringBootTest`: This annotation indicates that the test is an integration test and will start the entire Spring Boot application context.
    - `@DirtiesContext`: This annotation ensures that the application context is reset after each test method, ensuring a clean state for each test.
3. **Tests**:
    - `shouldReturnACashCardWhenDataIsSaved()`: Tests the retrieval of a specific `CashCard` by its ID.
    - `shouldNotReturnCashCardWithAnUnknownId()`: Tests the behavior when trying to retrieve a `CashCard` with an unknown ID.
    - `shouldCreateANewCashCard()`: Tests the creation of a new `CashCard` using a POST request.
    - `shouldReturnAllCashCardsWhenListIsRequested()`: Tests the retrieval of all `CashCard` objects.

### Differences:
1. **Test Focus**:
    - `CashCardJsonTest`: Focuses on JSON serialization and deserialization.
    - `CashCardApplicationTests`: Focuses on testing the actual behavior of the RESTful API endpoints.
2. **Test Level**:
    - `CashCardJsonTest`: Unit-level testing (focused on a specific functionality).
    - `CashCardApplicationTests`: Integration-level testing (tests the interaction between different components).

### Relation:
Both test classes are related to the `CashCard` functionality but from different perspectives. While `CashCardJsonTest` ensures that the object can be correctly represented as JSON and vice versa, `CashCardApplicationTests` ensures that the API behaves correctly when it receives HTTP requests related to `CashCard`.

In essence, `CashCardJsonTest` ensures the correctness of data format conversions, while `CashCardApplicationTests` ensures the correctness of the application's behavior in response to user actions.