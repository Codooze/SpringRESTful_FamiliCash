package example.cashcard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {
    private final CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
        // Fetch the CashCard from the repository using the provided ID
        Optional<CashCard> cashCardOptional = cashCardRepository.findById(requestedId);
        // Check if the CashCard was not found in the repository
        if (cashCardOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cashCardOptional.get());
    }
    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb) {
        // Save the new CashCard to the repository
        CashCard savedCashCard = cashCardRepository.save(newCashCardRequest);
        // Build the URI to the newly created CashCard
        URI locationOfNewCashCard = ucb.path("/cashcards/{id}").buildAndExpand(savedCashCard.id()).toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
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

*/

