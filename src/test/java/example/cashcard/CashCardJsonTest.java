package example.cashcard;


import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CashCardJsonTest {

    @Autowired
    private JacksonTester<CashCard> json;

    @Autowired
    private JacksonTester<CashCard[]> jsonList;

    private CashCard[] cashCards;

    @BeforeEach
    void setUp() {
        cashCards = Arrays.array(
                new CashCard(99L, 123.45, "sarah1"),
                new CashCard(100L, 1.00, "sarah1"),
                new CashCard(101L, 150.00, "sarah1"));
    }

    @Test
    public void cashCardSerializationTest() throws IOException {
        CashCard cashCard = cashCards[0];
        assertThat(json.write(cashCard)).isStrictlyEqualToJson("single.json");
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99);
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount")
                .isEqualTo(123.45);
    }

    @Test
    public void cashCardDeserializationTest() throws IOException {
        String expected = """
                {
                    "id": 99,
                    "amount": 123.45, 
                    "owner": "sarah1"
                }
                """;
        assertThat(json.parse(expected))
                .isEqualTo(new CashCard(99L, 123.45, "sarah1"));
        assertThat(json.parseObject(expected).id()).isEqualTo(99L);
        assertThat(json.parseObject(expected).amount()).isEqualTo(123.45);
    }

    @Test
    void cashCardListSerializationTest() throws IOException{
        assertThat(jsonList.write(cashCards)).isStrictlyEqualToJson("list.json"); // list.json is the expected output, cashCards is the actual output
    }

    @Test
    void cashCardListDeserializationTest() throws IOException {
        String expected = """
                [
                     {"id": 99, "amount": 123.45 , "owner": "sarah1"},
                     {"id": 100, "amount": 1.00 , "owner": "sarah1"},
                     {"id": 101, "amount": 150.00, "owner": "sarah1"}
                                                  
                ]
                """;
        assertThat(jsonList.parse(expected)).isEqualTo(cashCards); // cashCards is the expected output, expected is the actual output
    }

}

/*
json.write (Serialization):

Purpose: Converts an object into its JSON representation.
Direction: Object → JSON
Usage Scenario: When you have an object (or a list of objects) and you want to convert it into a JSON string for purposes such as:
Sending it as a response from a REST API.
Saving it to a file or database in JSON format.
Logging or debugging (to view the object's data in a readable format).
In the provided code, json.write(cashCard) is used to serialize the cashCard object into its JSON representation. The result is then compared to an expected JSON string to verify the serialization process.

json.parse (Deserialization):

Purpose: Converts a JSON representation back into its corresponding object.
Direction: JSON → Object
Usage Scenario: When you receive a JSON string and you want to convert it into an object (or a list of objects) for purposes such as:
Processing data received from a REST API request.
Reading data from a file or database stored in JSON format.
Constructing objects from configuration or settings stored in JSON.
In the provided code, json.parse(expected) is used to deserialize the expected JSON string into a CashCard object. The result is then compared to an actual CashCard object to verify the deserialization process.

In summary:

Use json.write when you have an object and want to get its JSON representation.
Use json.parse when you have a JSON string and want to get the corresponding object.
 */