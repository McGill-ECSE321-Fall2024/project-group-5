package ca.mcgill.ecse321.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        // cleaning up the database after each test
        paymentInformationRepository.deleteAll();
        customerAccountRepository.deleteAll();
    }

    @Test
    void testPersistPaymentInformation() {
        // create a new customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("password123");
        customerAccountRepository.save(someCustomer);

        // create and save new payment information associated with the customer
        PaymentInformation paymentInfo = new PaymentInformation();
        paymentInfo.setCardholderName("John Doe");
        paymentInfo.setCardNumber(123456789);
        paymentInfo.setExpirationDate(Date.valueOf("2025-12-31"));
        paymentInfo.setCvc(123);
        paymentInfo.setCardType(CardType.Visa);
        paymentInfo.setCustomerAccount(someCustomer);
        paymentInfo = paymentInformationRepository.save(paymentInfo);

        // read the payment info from the database by its ID
        Optional<PaymentInformation> retrievedPaymentInfo = paymentInformationRepository.findById(paymentInfo.getId());
        assertTrue(retrievedPaymentInfo.isPresent());
        assertEquals(paymentInfo.getCardholderName(), retrievedPaymentInfo.get().getCardholderName());
        assertEquals(paymentInfo.getCardNumber(), retrievedPaymentInfo.get().getCardNumber());
        assertEquals(paymentInfo.getExpirationDate(), retrievedPaymentInfo.get().getExpirationDate());
        assertEquals(paymentInfo.getCvc(), retrievedPaymentInfo.get().getCvc());
        assertEquals(paymentInfo.getCardType(), retrievedPaymentInfo.get().getCardType());
        assertEquals(paymentInfo.getCustomerAccount().getId(), retrievedPaymentInfo.get().getCustomerAccount().getId());
    }

    @Test
    void testFindPaymentInformationByCustomerAccountId() {
        // create a new customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("password456");
        customerAccountRepository.save(someCustomer);

        // create and save new payment information associated with the customer
        PaymentInformation paymentInfo = new PaymentInformation();
        paymentInfo.setCardholderName("Jane Doe");
        paymentInfo.setCardNumber(987654321);
        paymentInfo.setExpirationDate(Date.valueOf("2026-06-30"));
        paymentInfo.setCvc(456);
        paymentInfo.setCardType(CardType.Mastercard);
        paymentInfo.setCustomerAccount(someCustomer);
        paymentInformationRepository.save(paymentInfo);

        // Find payment information by customer account ID
        Iterable<PaymentInformation> paymentInfos = paymentInformationRepository
                .findByCustomerAccount_Id(someCustomer.getId());
        assertTrue(paymentInfos.iterator().hasNext());

        // verify the retrieved payment information
        PaymentInformation retrievedPaymentInfo = paymentInfos.iterator().next();
        assertEquals("Jane Doe", retrievedPaymentInfo.getCardholderName());
        assertEquals(987654321, retrievedPaymentInfo.getCardNumber());
    }

    @Test
    void testUpdatePaymentInformation() {
        // create a new customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("agent007");
        customerAccountRepository.save(someCustomer);

        // create and save new payment information associated with the customer
        PaymentInformation paymentInfo = new PaymentInformation();
        paymentInfo.setCardholderName("James Bond");
        paymentInfo.setCardNumber(700007);
        paymentInfo.setExpirationDate(Date.valueOf("2027-07-07"));
        paymentInfo.setCvc(777);
        paymentInfo.setCardType(CardType.AmericanExpress);
        paymentInfo.setCustomerAccount(someCustomer);
        paymentInformationRepository.save(paymentInfo);

        // retrieve the payment info and update the card number
        Optional<PaymentInformation> retrievedPaymentInfo = paymentInformationRepository.findById(paymentInfo.getId());
        assertTrue(retrievedPaymentInfo.isPresent());

        PaymentInformation paymentInfoToUpdate = retrievedPaymentInfo.get();
        paymentInfoToUpdate.setCardNumber(123123123);
        paymentInformationRepository.save(paymentInfoToUpdate);

        // check if the update was successful
        Optional<PaymentInformation> updatedPaymentInfo = paymentInformationRepository
                .findById(paymentInfoToUpdate.getId());
        assertTrue(retrievedPaymentInfo.isPresent());
        assertEquals(paymentInfoToUpdate.getCardholderName(), updatedPaymentInfo.get().getCardholderName());
        assertEquals(paymentInfoToUpdate.getCardNumber(), updatedPaymentInfo.get().getCardNumber());
        assertEquals(paymentInfoToUpdate.getExpirationDate(), updatedPaymentInfo.get().getExpirationDate());
        assertEquals(paymentInfoToUpdate.getCvc(), updatedPaymentInfo.get().getCvc());
        assertEquals(paymentInfoToUpdate.getCardType(), updatedPaymentInfo.get().getCardType());
        assertEquals(paymentInfoToUpdate.getCustomerAccount().getId(),
                updatedPaymentInfo.get().getCustomerAccount().getId());
    }

    @Test
    void testDeletePaymentInformation() {
        // create a new customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("delete123");
        customerAccountRepository.save(someCustomer);

        // create and save new payment information associated with the customer
        PaymentInformation paymentInfo = new PaymentInformation();
        paymentInfo.setCardholderName("Delete Test");
        paymentInfo.setCardNumber(101010101);
        paymentInfo.setExpirationDate(Date.valueOf("2028-10-10"));
        paymentInfo.setCvc(101);
        paymentInfo.setCardType(CardType.Interact);
        paymentInfo.setCustomerAccount(someCustomer);
        paymentInformationRepository.save(paymentInfo);

        // check if the payment info exists
        Optional<PaymentInformation> retrievedPaymentInfo = paymentInformationRepository.findById(paymentInfo.getId());
        assertTrue(retrievedPaymentInfo.isPresent());

        // delete the payment info
        paymentInformationRepository.deleteById(paymentInfo.getId());

        // verify if the payment info was deleted
        Optional<PaymentInformation> deletedPaymentInfo = paymentInformationRepository.findById(paymentInfo.getId());
        assertTrue(deletedPaymentInfo.isEmpty());
    }

    @Test
    void testFindByInvalidId() {
        // try to retrieve payment info with an invalid ID
        Optional<PaymentInformation> nonExistentPaymentInfo = paymentInformationRepository.findById(999);
        assertTrue(nonExistentPaymentInfo.isEmpty());
    }
}
