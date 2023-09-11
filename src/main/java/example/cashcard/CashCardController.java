package example.cashcard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {
    private final CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<CashCard> findById(@PathVariable Long requestedId, Principal principal) {
        CashCard cashCard = findCashCard(requestedId, principal);
        //cashCardRepository.findByIdAndOwner(requestedId, principal.getName());
        if (cashCard != null) {
            return ResponseEntity.ok(cashCard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb, Principal principal) {
        CashCard cashCardWithOwner = new CashCard(null, newCashCardRequest.amount(), principal.getName());
        // Save the new CashCard to the repository
        CashCard savedCashCard = cashCardRepository.save(cashCardWithOwner);
        // Build the URI to the newly created CashCard
        URI locationOfNewCashCard = ucb.path("/cashcards/{id}").buildAndExpand(savedCashCard.id()).toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

/*    @GetMapping()
    public ResponseEntity<Iterable<CashCard>> findAll() {
        return ResponseEntity.ok(cashCardRepository.findAll());
    }*/


    @GetMapping
    public ResponseEntity<List<CashCard>> findAll(Pageable pageable, Principal principal) {
        Page<CashCard> page = cashCardRepository.findByOwner(
                principal.getName(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC,"amount")) // default sort by ASC direction
                ));
        return ResponseEntity.ok(page.getContent());
    }

    @PutMapping("/{requestedId}")
    private ResponseEntity<Void> putCashCard(@PathVariable Long requestedId, @RequestBody CashCard cashCardUpdate, Principal principal) {
        CashCard cashCard = findCashCard(requestedId, principal);
        //cashCardRepository.findByIdAndOwner(requestedId, principal.getName());
        if (cashCard != null) {
            CashCard updatedCashCard = new CashCard(cashCard.id(), cashCardUpdate.amount(), principal.getName());
            cashCardRepository.save(updatedCashCard);
            return ResponseEntity.noContent().build();
        }
        // just return 204 NO CONTENT for now.
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteCashCard(@PathVariable Long id, Principal principal) {
        if (!cashCardRepository.existsByIdAndOwner(id, principal.getName())) {
            return ResponseEntity.notFound().build();
        }
        cashCardRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    private CashCard findCashCard(Long requestedId, Principal principal) {
        return cashCardRepository.findByIdAndOwner(requestedId, principal.getName());
    }
}
/*
createCashCard
UriComponentsBuilder ucb: This is a helper object provided by Spring that aids in building URIs.
It's useful for creating the location header in the response, which points to the newly created resource.

URI locationOfNewCashCard = ucb.path("/cashcards/{id}").buildAndExpand(savedCashCard.id()).toUri():
ucb.path("/cashcards/{id}"): This sets the path to which the new URI should point.
The {id} is a placeholder for the ID of the newly created CashCard.
.buildAndExpand(savedCashCard.id()): This method replaces the {id} placeholder in the path with the actual ID of the savedCashCard.
.toUri(): This converts the UriComponents instance to a URI object.

putCashCards method: 👈
1. Endpoint Security:
The @PutMapping("/{requestedId}") annotation indicates that this method handles HTTP PUT requests to the /cashcards/{requestedId} endpoint. The {requestedId} is a path variable that represents the ID of the CashCard to be updated.

2. Principal:
The Principal parameter in the method signature represents the currently authenticated user. The Principal object contains information about the user, such as their username. In the context of Spring Security, when a user is authenticated, their details are stored in a Principal object which can be injected into controller methods.

3. Ownership Check:
The line:

java
Copy code
CashCard cashCard = cashCardRepository.findByIdAndOwner(requestedId, principal.getName());
fetches the CashCard with the specified requestedId and ensures that the owner of the CashCard matches the currently authenticated user's username (principal.getName()). This is a crucial security check that ensures that users can only update CashCard records that they own.

If the CashCard with the given requestedId does not belong to the authenticated user, the cashCardRepository.findByIdAndOwner method will return null, and no update will be performed.

4. Data Update:
The following lines create a new CashCard object with the updated amount while retaining the original ID and owner:

java
Copy code
CashCard updatedCashCard = new CashCard(cashCard.id(), cashCardUpdate.amount(), principal.getName());
Then, the updated CashCard object is saved to the repository:

java
Copy code
cashCardRepository.save(updatedCashCard);
This ensures that only the amount is updated, and the owner remains the same.

5. Response:
The method returns a 204 No Content HTTP status code, indicating that the request was successful, but there's no content to send in the response:

java
Copy code
return ResponseEntity.noContent().build();
Summary:
The security in this method is primarily based on ensuring that users can only update CashCard records they own. This is achieved by using the Principal to get the authenticated user's username and then checking ownership using the findByIdAndOwner method of the cashCardRepository. If the CashCard doesn't belong to the authenticated user, no update will occur.
:
*/

