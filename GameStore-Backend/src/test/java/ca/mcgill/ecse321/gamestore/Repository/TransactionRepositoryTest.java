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

import ca.mcgill.ecse321.gamestore.dao.TransactionRepository;
import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.gamestore.dao.PaymentInformationRepository;
import ca.mcgill.ecse321.gamestore.dao.AddressRepository;
import ca.mcgill.ecse321.gamestore.model.Transaction;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation;
import ca.mcgill.ecse321.gamestore.model.Address;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation.CardType;

@SpringBootTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Autowired
    private PaymentInformationRepository paymentInformationRepository;

    @Autowired
    private AddressRepository addressRepository;

    @AfterEach
    public void clearDatabase() {
        // clear everything after each test
        transactionRepository.deleteAll();
        paymentInformationRepository.deleteAll();
        addressRepository.deleteAll();
        customerAccountRepository.deleteAll();
    }

    @Test
    void testPersistTransaction() {
        // set up customer
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("password123");
        customerAccountRepository.save(someCustomer);

        // set up payment info
        PaymentInformation paymentInfo = new PaymentInformation();
        paymentInfo.setCardholderName("someCustomer");
        paymentInfo.setCardNumber(123456789);
        paymentInfo.setExpirationDate(Date.valueOf("2025-12-31"));
        paymentInfo.setCvc(123);
        paymentInfo.setCardType(CardType.Visa);
        paymentInfo.setCustomerAccount(someCustomer);
        paymentInformationRepository.save(paymentInfo);

        // create an address
        Address address = new Address();
        address.setAddress("123 Main St");
        address.setCity("Montreal");
        address.setProvince("Quebec");
        address.setCountry("Canada");
        address.setPostalCode("H3A1B2");
        address.setCustomerAccount(someCustomer);
        addressRepository.save(address);

        // create transaction and link it with customer, payment info, cart, and address
        Transaction transaction = new Transaction();
        transaction.setTotalPrice(100.0);
        transaction.setIsPaid(true);
        transaction.setDeliveryStatus(false);
        transaction.setPromotionCode("PROMO123");
        transaction.setUserAgreementCheck(true);
        transaction.setCustomerAccount(someCustomer);
        transaction.setPaymentInformation(paymentInfo);
        transaction.setAddress(address);
        transaction = transactionRepository.save(transaction);

        // make sure the transaction was saved correctly
        assertNotNull(transaction);
        assertEquals(100.0, transaction.getTotalPrice());
        assertEquals(true, transaction.getIsPaid());
        assertEquals(false, transaction.getDeliveryStatus());
        assertEquals("PROMO123", transaction.getPromotionCode());
        assertEquals(someCustomer.getUsername(), transaction.getCustomerAccount().getUsername());

        // get the transaction from the database and make sure it matches
        Optional<Transaction> retrievedTransaction = transactionRepository.findById(transaction.getTransactionId());
        assertTrue(retrievedTransaction.isPresent());
        assertEquals(100.0, retrievedTransaction.get().getTotalPrice());
        assertEquals(someCustomer.getUsername(), retrievedTransaction.get().getCustomerAccount().getUsername());
    }

    @Test
    void testFindTransactionByCustomerAccountId() {
        // create a customer
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("password456");
        customerAccountRepository.save(someCustomer);

        // create payment info
        PaymentInformation paymentInfo = new PaymentInformation();
        paymentInfo.setCardholderName("someCustomer");
        paymentInfo.setCardNumber(987654321);
        paymentInfo.setExpirationDate(Date.valueOf("2026-06-30"));
        paymentInfo.setCvc(456);
        paymentInfo.setCardType(CardType.Mastercard);
        paymentInfo.setCustomerAccount(someCustomer);
        paymentInformationRepository.save(paymentInfo);

        // create an address
        Address address = new Address();
        address.setAddress("456 Another St");
        address.setCity("Montreal");
        address.setProvince("Quebec");
        address.setCountry("Canada");
        address.setPostalCode("H4B1A1");
        address.setCustomerAccount(someCustomer);
        addressRepository.save(address);

        // create an transaction
        Transaction transaction = new Transaction();
        transaction.setTotalPrice(150.0);
        transaction.setIsPaid(true);
        transaction.setDeliveryStatus(false);
        transaction.setPromotionCode("DISCOUNT123");
        transaction.setUserAgreementCheck(true);
        transaction.setCustomerAccount(someCustomer);
        transaction.setPaymentInformation(paymentInfo);
        transaction.setAddress(address);
        transactionRepository.save(transaction);

        // find the transaction by customer account ID
        Iterable<Transaction> transactions = transactionRepository
                .findByCustomerAccount_Id(someCustomer.getId());
        assertTrue(transactions.iterator().hasNext());

        Transaction retrievedTransaction = transactions.iterator().next();
        assertEquals(150.0, retrievedTransaction.getTotalPrice());
        assertEquals(someCustomer.getUsername(), retrievedTransaction.getCustomerAccount().getUsername());
    }

    @Test
    void testUpdateTransaction() {
        // create a customer
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("agent007");
        customerAccountRepository.save(someCustomer);

        // create payment info
        PaymentInformation paymentInfo = new PaymentInformation();
        paymentInfo.setCardholderName("someCustomer");
        paymentInfo.setCardNumber(700007);
        paymentInfo.setExpirationDate(Date.valueOf("2027-07-07"));
        paymentInfo.setCvc(777);
        paymentInfo.setCardType(CardType.AmericanExpress);
        paymentInfo.setCustomerAccount(someCustomer);
        paymentInformationRepository.save(paymentInfo);

        // create an address
        Address address = new Address();
        address.setAddress("789 Spy St");
        address.setCity("London");
        address.setProvince("England");
        address.setCountry("UK");
        address.setPostalCode("W1A1AA");
        address.setCustomerAccount(someCustomer);
        addressRepository.save(address);

        // create transaction
        Transaction transaction = new Transaction();
        transaction.setTotalPrice(200.0);
        transaction.setIsPaid(true);
        transaction.setDeliveryStatus(true);
        transaction.setPromotionCode("TOPSECRET123");
        transaction.setUserAgreementCheck(true);
        transaction.setCustomerAccount(someCustomer);
        transaction.setPaymentInformation(paymentInfo);
        transaction.setAddress(address);
        transactionRepository.save(transaction);

        // update the transaction's total price
        Optional<Transaction> retrievedTransaction = transactionRepository.findById(transaction.getTransactionId());
        assertTrue(retrievedTransaction.isPresent());
        retrievedTransaction.get().setTotalPrice(250.0);
        transactionRepository.save(retrievedTransaction.get());

        // verify the transaction was updated
        Optional<Transaction> updatedTransaction = transactionRepository
                .findById(retrievedTransaction.get().getTransactionId());
        assertTrue(updatedTransaction.isPresent());
        assertEquals(250.0, updatedTransaction.get().getTotalPrice());
    }

    @Test
    void testDeleteTransaction() {
        // create a customer
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("delete123");
        customerAccountRepository.save(someCustomer);

        // create payment info
        PaymentInformation paymentInfo = new PaymentInformation();
        paymentInfo.setCardholderName("someCustomer");
        paymentInfo.setCardNumber(101010101);
        paymentInfo.setExpirationDate(Date.valueOf("2028-10-10"));
        paymentInfo.setCvc(101);
        paymentInfo.setCardType(CardType.Interact);
        paymentInfo.setCustomerAccount(someCustomer);
        paymentInformationRepository.save(paymentInfo);

        // create an address
        Address address = new Address();
        address.setAddress("123 Deletion St");
        address.setCity("Montreal");
        address.setProvince("Quebec");
        address.setCountry("Canada");
        address.setPostalCode("H3Z2Y7");
        address.setCustomerAccount(someCustomer);
        addressRepository.save(address);

        // create transaction
        Transaction transaction = new Transaction();
        transaction.setTotalPrice(175.0);
        transaction.setIsPaid(true);
        transaction.setDeliveryStatus(false);
        transaction.setPromotionCode("DELETE123");
        transaction.setUserAgreementCheck(true);
        transaction.setCustomerAccount(someCustomer);
        transaction.setPaymentInformation(paymentInfo);
        transaction.setAddress(address);
        transactionRepository.save(transaction);

        // verify the transaction exists
        Optional<Transaction> retrievedTransaction = transactionRepository.findById(transaction.getTransactionId());
        assertTrue(retrievedTransaction.isPresent());

        // delete the transaction
        transactionRepository.deleteById(transaction.getTransactionId());

        // verify the transaction was deleted
        Optional<Transaction> deletedTransaction = transactionRepository.findById(transaction.getTransactionId());
        assertTrue(deletedTransaction.isEmpty());
    }
}