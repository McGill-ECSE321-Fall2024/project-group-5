package ca.mcgill.ecse321.gamestore.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.gamestore.dao.TransactionRepository;
import ca.mcgill.ecse321.gamestore.dto.CustomerAccountResponseDto;
import ca.mcgill.ecse321.gamestore.dto.TransactionRequestDto;
import ca.mcgill.ecse321.gamestore.dto.TransactionResponseDto;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.service.CustomerAccountService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TransactionIntegrationTests {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Autowired
    private CustomerAccountService customerAccountService;

    @Autowired
    private TestRestTemplate client;

    private final String VALID_NAME = "Alice";
    private final String VALID_EMAIL = "alice@mail.mcgill.ca";
    private final String VALID_PASSWORD = "Password123%";

    @BeforeAll
    @AfterAll
    public void clearDatabase() {
        transactionRepository.deleteAll();
        customerAccountRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateValidTransaction() throws Exception {
        // Arrange
        // create and persist necessary objects for creation of Transaction
        CustomerAccount accountModel = customerAccountService.createCustomerAccount(VALID_NAME, VALID_EMAIL,
                VALID_PASSWORD);
        TransactionRequestDto request = new TransactionRequestDto(0, false, false, false, null, null, null);
        CustomerAccountResponseDto account = new CustomerAccountResponseDto(accountModel);
        request.setCustomerAccount(account);

        // Act
        ResponseEntity<TransactionResponseDto> response = client.postForEntity("api/transaction/create", request,
                TransactionResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        TransactionResponseDto createdTransaction = response.getBody();
        assertNotNull(createdTransaction);
        assertEquals(account.getId(),
                createdTransaction.getCustomerAccount().getId());
        assertTrue(createdTransaction.getTransactionId() >= 0);
    }

    @Test
    @Order(2)
    public void testReadTransactionByValidId() {
    }

    // Tests creating a transaciton with invalid customer account
    @Test
    @Order(3)
    public void testCreateTransactionWithInvalidAccount() {

    }

    @Test
    @Order(4)
    public void testFindTransactionsByCustomer() {

    }

    @Test
    @Order(5)
    public void testFindTransactionsByIsPaid() {

    }

    @Test
    @Order(6)
    public void testFindTransactionsByDeliveryStatus() {

    }

    @Test
    @Order(7)
    public void testDeleteTransaction() {

    }

    @Test
    @Order(8)
    public void testUpdateTransaction() {

    }
}