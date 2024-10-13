package ca.mcgill.ecse321.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamestore.dao.PaymentInformationRepository;
import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation.CardType;

@SpringBootTest
class PaymentInformationRepositoryTest {

    @Autowired
    private PaymentInformationRepository paymentInformationRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @AfterEach
    public void clearDatabase() {
        paymentInformationRepository.deleteAll();
        customerAccountRepository.deleteAll();
    }

    @Test
    void testPersistPaymentInformation() {
        // Create a new customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("password123");
        customerAccountRepository.save(someCustomer);

        // Create a new PaymentInformation and associate it with the customer account
        PaymentInformation paymentInfo = new PaymentInformation();
        paymentInfo.setCardholderName("John Doe");
        paymentInfo.setCardNumber(123456789);
        paymentInfo.setExpirationDate(Date.valueOf("2025-12-31"));
        paymentInfo.setCvc(123);
        paymentInfo.setCardType(CardType.Visa);
        paymentInfo.setCustomerAccount(someCustomer);
        paymentInfo = paymentInformationRepository.save(paymentInfo);

        // Verify that the payment information was saved correctly
        assertNotNull(paymentInfo);
        assertEquals("John Doe", paymentInfo.getCardholderName());
        assertEquals(123456789, paymentInfo.getCardNumber());
        assertEquals("2025-12-31", paymentInfo.getExpirationDate().toString());
        assertEquals(123, paymentInfo.getCvc());
        assertEquals(CardType.Visa, paymentInfo.getCardType());
        assertEquals(someCustomer.getUsername(), paymentInfo.getCustomerAccount().getUsername());

        // Read the payment information from the database using the ID
        Optional<PaymentInformation> retrievedPaymentInfo = paymentInformationRepository.findById(paymentInfo.getId());
        assertTrue(retrievedPaymentInfo.isPresent());
        assertEquals("John Doe", retrievedPaymentInfo.get().getCardholderName());
        assertEquals(123456789, retrievedPaymentInfo.get().getCardNumber());
    }

    @Test
    void testFindPaymentInformationByCustomerAccountId() {
        // Create a new customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("password456");
        customerAccountRepository.save(someCustomer);

        // Create a new PaymentInformation and associate it with the customer account
        PaymentInformation paymentInfo = new PaymentInformation();
        paymentInfo.setCardholderName("Jane Doe");
        paymentInfo.setCardNumber(987654321);
        paymentInfo.setExpirationDate(Date.valueOf("2026-06-30"));
        paymentInfo.setCvc(456);
        paymentInfo.setCardType(CardType.Mastercard);
        paymentInfo.setCustomerAccount(someCustomer);
        paymentInformationRepository.save(paymentInfo);

        // Find payment information by customer account ID
        Iterable<PaymentInformation> paymentInfos = paymentInformationRepository.findByCustomerAccount_CustomerAccountId(someCustomer.getId());
        assertTrue(paymentInfos.iterator().hasNext());

        PaymentInformation retrievedPaymentInfo = paymentInfos.iterator().next();
        assertEquals("Jane Doe", retrievedPaymentInfo.getCardholderName());
        assertEquals(987654321, retrievedPaymentInfo.getCardNumber());
    }

    @Test
    void testUpdatePaymentInformation() {
        // Create a new customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("agent007");
        customerAccountRepository.save(someCustomer);

        // Create a new PaymentInformation and associate it with the customer account
        PaymentInformation paymentInfo = new PaymentInformation();
        paymentInfo.setCardholderName("James Bond");
        paymentInfo.setCardNumber(700007);
        paymentInfo.setExpirationDate(Date.valueOf("2027-07-07"));
        paymentInfo.setCvc(777);
        paymentInfo.setCardType(CardType.AmericanExpress);
        paymentInfo.setCustomerAccount(someCustomer);
        paymentInformationRepository.save(paymentInfo);

        // Retrieve the payment information and update the card number
        Optional<PaymentInformation> retrievedPaymentInfo = paymentInformationRepository.findById(paymentInfo.getId());
        assertTrue(retrievedPaymentInfo.isPresent());

        PaymentInformation paymentInfoToUpdate = retrievedPaymentInfo.get();
        paymentInfoToUpdate.setCardNumber(123123123);
        paymentInformationRepository.save(paymentInfoToUpdate);

        // Verify the payment information was updated
        Optional<PaymentInformation> updatedPaymentInfo = paymentInformationRepository.findById(paymentInfoToUpdate.getId());
        assertTrue(updatedPaymentInfo.isPresent());
        assertEquals(123123123, updatedPaymentInfo.get().getCardNumber());
    }

    @Test
    void testDeletePaymentInformation() {
        // Create a new customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("delete123");
        customerAccountRepository.save(someCustomer);

        // Create a new PaymentInformation and associate it with the customer account
        PaymentInformation paymentInfo = new PaymentInformation();
        paymentInfo.setCardholderName("Delete Test");
        paymentInfo.setCardNumber(101010101);
        paymentInfo.setExpirationDate(Date.valueOf("2028-10-10"));
        paymentInfo.setCvc(101);
        paymentInfo.setCardType(CardType.Interact);
        paymentInfo.setCustomerAccount(someCustomer);
        paymentInformationRepository.save(paymentInfo);

        // Verify the payment information exists
        Optional<PaymentInformation> retrievedPaymentInfo = paymentInformationRepository.findById(paymentInfo.getId());
        assertTrue(retrievedPaymentInfo.isPresent());

        // Delete the payment information
        paymentInformationRepository.deleteById(paymentInfo.getId());

        // Verify the payment information was deleted
        Optional<PaymentInformation> deletedPaymentInfo = paymentInformationRepository.findById(paymentInfo.getId());
        assertTrue(deletedPaymentInfo.isEmpty());
    }

    @Test
    void testFindByInvalidId() {
        // Attempt to retrieve a payment information with a non-existent ID
        Optional<PaymentInformation> nonExistentPaymentInfo = paymentInformationRepository.findById(999);
        assertTrue(nonExistentPaymentInfo.isEmpty());
    }
}
