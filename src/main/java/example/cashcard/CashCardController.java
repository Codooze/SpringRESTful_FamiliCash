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
        // Fetch the CashCard from the repository using the provided ID
        Optional<CashCard> cashCardOptional = Optional.ofNullable(cashCardRepository.findByIdAndOwner(requestedId, principal.getName()));
        // Check if the CashCard was not found in the repository
        if (cashCardOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cashCardOptional.get());
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

