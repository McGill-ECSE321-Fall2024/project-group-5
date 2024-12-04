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

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TransactionIntegrationTests {
        @Autowired
        private TransactionRepository transactionRepository;

        @Autowired
        private CustomerAccountRepository customerAccountRepository;

        @Autowired
        private TestRestTemplate client;

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
                CustomerAccount customerAccount = new CustomerAccount();
                customerAccount.setEmail("bob@gmail.com");
                customerAccount.setName("bob");
                customerAccount.setUsername("bob123");
                customerAccount.setRandomPassword("aRandomPassword");
                customerAccount.setPasswordHash("thePasswordHash");

                TransactionRequestDto request = new TransactionRequestDto(0, false, false, false, null, null, null);
                CustomerAccountResponseDto account = new CustomerAccountResponseDto(customerAccount);
                request.setCustomerAccount(account);

                // Act
                ResponseEntity<TransactionResponseDto> response = client.postForEntity("/api/transaction/create/",
                                request,
                                TransactionResponseDto.class);

                // Assert
                assertNotNull(response.getBody());
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                TransactionResponseDto createdTransaction = response.getBody();
                assertNotNull(createdTransaction);
                assertEquals(account.getId(),
                                createdTransaction.getCustomerAccount().getId());
                assertTrue(createdTransaction.getTransactionId() >= 0);
        }

        @SuppressWarnings("null")
        @Test
        @Order(2)
        public void testReadTransactionByValidId() throws Exception {
                // Arrange
                CustomerAccount customerAccount = new CustomerAccount();
                customerAccount.setEmail("bob@gmail.com");
                customerAccount.setName("bob");
                customerAccount.setUsername("bob123");
                customerAccount.setRandomPassword("aRandomPassword");
                customerAccount.setPasswordHash("thePasswordHash");

                TransactionRequestDto request = new TransactionRequestDto(0, false, false, false, null, null, null);
                request.setCustomerAccount(new CustomerAccountResponseDto(customerAccount));

                ResponseEntity<TransactionResponseDto> createResponse = client.postForEntity("/api/transaction/create/",
                                request,
                                TransactionResponseDto.class);
                TransactionResponseDto createdTransaction = createResponse.getBody();
                assertNotNull(createdTransaction);

                // Act
                ResponseEntity<TransactionResponseDto> response = client.getForEntity(
                                "/api/transaction/get/" + createdTransaction.getTransactionId(),
                                TransactionResponseDto.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(createdTransaction.getTransactionId(), response.getBody().getTransactionId());
                assertEquals(createdTransaction.getCustomerAccount().getId(),
                                response.getBody().getCustomerAccount().getId());
        }

        // Tests creating a transaciton with invalid customer account
        @Test
        @Order(3)
        public void testCreateTransactionWithInvalidAccount() {
                // Arrange
                TransactionRequestDto request = new TransactionRequestDto(0, false, false, false, null, null, null);

                // Act
                ResponseEntity<TransactionResponseDto> response = client.postForEntity("/api/transaction/create/",
                                request,
                                TransactionResponseDto.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @SuppressWarnings("null")
        @Test
        @Order(4)
        public void testFindTransactionsByCustomer() throws Exception {
                // Arrange
                CustomerAccount customerAccount = new CustomerAccount();
                customerAccount.setEmail("bob@gmail.com");
                customerAccount.setName("bob");
                customerAccount.setUsername("bob123");
                customerAccount.setRandomPassword("aRandomPassword");
                customerAccount.setPasswordHash("thePasswordHash");

                TransactionRequestDto request = new TransactionRequestDto(0, false, false, false, null, null, null);
                request.setCustomerAccount(new CustomerAccountResponseDto(customerAccount));

                client.postForEntity("/api/transaction/create/", request, TransactionResponseDto.class);

                // Act
                ResponseEntity<TransactionResponseDto[]> response = client
                                .getForEntity("/api/transaction/find-by-customer/" + customerAccount.getId(),
                                                TransactionResponseDto[].class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertTrue(response.getBody().length > 0);
                assertEquals(customerAccount.getId(), response.getBody()[0].getCustomerAccount().getId());
        }

        @SuppressWarnings("null")
        @Test
        @Order(5)
        public void testFindTransactionsByIsPaid() {
                // Act
                ResponseEntity<TransactionResponseDto[]> response = client.getForEntity(
                                "/api/transaction/find-by-is-paid/false/",
                                TransactionResponseDto[].class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertTrue(response.getBody().length >= 0);
        }

        @SuppressWarnings("null")
        @Test
        @Order(6)
        public void testFindTransactionsByDeliveryStatus() {
                // Act
                ResponseEntity<TransactionResponseDto[]> response = client
                                .getForEntity("/api/transaction/find-by-delivery-status/false/",
                                                TransactionResponseDto[].class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertTrue(response.getBody().length >= 0);
        }

        @Test
        @Order(7)
        public void testDeleteTransaction() throws Exception {
                // Arrange
                CustomerAccount customerAccount = new CustomerAccount();
                customerAccount.setEmail("bob@gmail.com");
                customerAccount.setName("bob");
                customerAccount.setUsername("bob123");
                customerAccount.setRandomPassword("aRandomPassword");
                customerAccount.setPasswordHash("thePasswordHash");

                TransactionRequestDto request = new TransactionRequestDto(0, false, false, false, null, null, null);
                request.setCustomerAccount(new CustomerAccountResponseDto(customerAccount));

                ResponseEntity<TransactionResponseDto> createResponse = client.postForEntity("/api/transaction/create/",
                                request,
                                TransactionResponseDto.class);
                TransactionResponseDto createdTransaction = createResponse.getBody();
                assertNotNull(createdTransaction);

                // Act
                client.delete("/api/transaction/delete/" + createdTransaction.getTransactionId());

                // Assert
                ResponseEntity<TransactionResponseDto> response = client.getForEntity(
                                "/api/transaction/get/" + createdTransaction.getTransactionId(),
                                TransactionResponseDto.class);
                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @SuppressWarnings("null")
        @Test
        @Order(8)
        public void testUpdateTransaction() throws Exception {
                // Arrange
                CustomerAccount customerAccount = new CustomerAccount();
                customerAccount.setEmail("bob@gmail.com");
                customerAccount.setName("bob");
                customerAccount.setUsername("bob123");
                customerAccount.setRandomPassword("aRandomPassword");
                customerAccount.setPasswordHash("thePasswordHash");

                TransactionRequestDto request = new TransactionRequestDto(0, false, false, false, null, null, null);
                request.setCustomerAccount(new CustomerAccountResponseDto(customerAccount));

                ResponseEntity<TransactionResponseDto> createResponse = client.postForEntity("/api/transaction/create/",
                                request,
                                TransactionResponseDto.class);
                TransactionResponseDto createdTransaction = createResponse.getBody();
                assertNotNull(createdTransaction);

                createdTransaction.setIsPaid(true);

                // Act
                client.put("/api/transaction/update/", createdTransaction);

                // Assert
                ResponseEntity<TransactionResponseDto> updatedResponse = client.getForEntity(
                                "/api/transaction/get/" + createdTransaction.getTransactionId(),
                                TransactionResponseDto.class);
                assertEquals(HttpStatus.OK, updatedResponse.getStatusCode());
                assertTrue(updatedResponse.getBody().getIsPaid());
        }
}