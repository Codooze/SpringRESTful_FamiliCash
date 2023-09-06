package example.cashcard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        if(cashCardOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cashCardOptional.get());
        }
    }