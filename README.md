# Simple Spring Security 

## Description
In this lab you will complete the following objectives:

- Understand our security requirements
- Add the Spring Security dependency
- Configure and test Basic Authentication
- Configure and support Authorization
- Update our Repository to support filtering by owner
- Utilize our Repository updates in our Controller to enhance security
- Ensure only the correct Cash Card owner can create and own new Cash Cards
- Learn how we deal with Cross-Site Request Forgery (CSRF)

### The logic will be something like this:
The logic will be something like this:

IF the user is authenticated

... AND they are authorized as a "card owner"

... ... AND they own the requested Cash Card

THEN complete the users's request

BUT do not allow users to access Cash Cards they do not own.

## Important changes
- owner added as a field to the CashCard Java record.
- owner added to all .sql files in src/test/resources/
- owner added to all .json files in src/test/resources/example/cashcard


## Notes to improve understanding
### SecurityConfig.java

The `UserDetailsService` is an interface provided by Spring Security to look up and authenticate users. It's a core component in Spring Security's authentication mechanism. When a user tries to authenticate, Spring Security will use the `UserDetailsService` to retrieve the user's information to perform the authentication.

Let's break down the method `testOnlyUsers` and its relation to the rest of the code:

### `testOnlyUsers` Method:

1. **Naming**: The method is named `testOnlyUsers`, which suggests that it's intended for testing purposes. This is further supported by the fact that it uses `InMemoryUserDetailsManager`, which is a simple in-memory store for user details, often used in testing scenarios or simple applications.

2. **Functionality**: This method defines two users (`sarah1` and `hank-owns-no-cards`) with their respective passwords and roles. The passwords are encoded using the `PasswordEncoder` bean, ensuring they are securely stored.

3. **Return Value**: It returns an instance of `InMemoryUserDetailsManager` initialized with the two users. This manager will serve user details when authentication is attempted.

### Relation with the Rest of the Code:

1. **PasswordEncoder**: The `testOnlyUsers` method takes a `PasswordEncoder` as a parameter. This encoder is defined in the `passwordEncoder` bean, which returns an instance of `BCryptPasswordEncoder`. This encoder is used to hash the passwords of the users.

2. **SecurityFilterChain**: The `filterChain` bean defines the security rules for the application. In this case, it specifies that only users with the role `CARD-OWNER` can access the `/cashcards/**` endpoint. The authentication mechanism relies on the `UserDetailsService` to retrieve user details. When a user tries to authenticate, Spring Security will use the `UserDetailsService` (in this case, `testOnlyUsers`) to fetch the user's details and verify the provided credentials.

3. **Integration with Spring Security**: The `UserDetailsService` bean (`testOnlyUsers`) is automatically picked up by Spring Security for authentication. When a user tries to log in, Spring Security will use this service to retrieve the user's details based on the provided username. It will then compare the provided password (after hashing) with the stored password to authenticate the user.

### Conclusion:

The `testOnlyUsers` method is likely named for clarity, indicating that it's intended for testing purposes. In a real-world application, you'd typically replace the `InMemoryUserDetailsManager` with a more persistent store, like a database, to manage user details. The method is crucial for the authentication process, as it provides the user details that Spring Security relies on to authenticate users.