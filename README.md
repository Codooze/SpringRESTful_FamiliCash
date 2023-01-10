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