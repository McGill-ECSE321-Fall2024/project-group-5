package ca.mcgill.ecse321.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
        transaction.setUserAgreementCheck(true);
        transaction.setCustomerAccount(someCustomer);
        transaction.setPaymentInformation(paymentInfo);
        transaction.setAddress(address);
        transaction = transactionRepository.save(transaction);

        // get the transaction from the database and make sure it matches
        Transaction retrievedTransaction = transactionRepository
                .findTransactiontByTransactionId(transaction.getTransactionId());
        assertNotNull(retrievedTransaction);
        assertEquals(transaction.getTotalPrice(), retrievedTransaction.getTotalPrice());
        assertEquals(transaction.getIsPaid(), retrievedTransaction.getIsPaid());
        assertEquals(transaction.getAddress().getId(), retrievedTransaction.getAddress().getId());
        assertEquals(transaction.getCustomerAccount().getId(), retrievedTransaction.getCustomerAccount().getId());
        assertEquals(transaction.getUserAgreementCheck(), retrievedTransaction.getUserAgreementCheck());
        assertEquals(transaction.getPaymentInformation().getId(), retrievedTransaction.getPaymentInformation().getId());
        assertEquals(transaction.getTransactionId(), retrievedTransaction.getTransactionId());
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
        assertNotNull(retrievedTransaction);
        assertEquals(transaction.getTotalPrice(), retrievedTransaction.getTotalPrice());
        assertEquals(transaction.getIsPaid(), retrievedTransaction.getIsPaid());
        assertEquals(transaction.getAddress().getId(), retrievedTransaction.getAddress().getId());
        assertEquals(transaction.getCustomerAccount().getId(), retrievedTransaction.getCustomerAccount().getId());
        assertEquals(transaction.getUserAgreementCheck(), retrievedTransaction.getUserAgreementCheck());
        assertEquals(transaction.getPaymentInformation().getId(), retrievedTransaction.getPaymentInformation().getId());
        assertEquals(transaction.getTransactionId(), retrievedTransaction.getTransactionId());
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
        transaction.setUserAgreementCheck(true);
        transaction.setCustomerAccount(someCustomer);
        transaction.setPaymentInformation(paymentInfo);
        transaction.setAddress(address);
        transactionRepository.save(transaction);

        // update the transaction's total price
        Transaction retrievedTransaction = transactionRepository
                .findTransactiontByTransactionId(transaction.getTransactionId());
        assertNotNull(retrievedTransaction);
        retrievedTransaction.setTotalPrice(250.0);
        transactionRepository.save(retrievedTransaction);

        // verify the transaction was updated
        Transaction updatedTransaction = transactionRepository
                .findTransactiontByTransactionId(retrievedTransaction.getTransactionId());
        assertNotNull(retrievedTransaction);
        assertEquals(retrievedTransaction.getTotalPrice(), updatedTransaction.getTotalPrice());
        assertEquals(retrievedTransaction.getIsPaid(), updatedTransaction.getIsPaid());
        assertEquals(retrievedTransaction.getAddress().getId(), updatedTransaction.getAddress().getId());
        assertEquals(retrievedTransaction.getCustomerAccount().getId(),
                updatedTransaction.getCustomerAccount().getId());
        assertEquals(retrievedTransaction.getUserAgreementCheck(), updatedTransaction.getUserAgreementCheck());
        assertEquals(retrievedTransaction.getPaymentInformation().getId(),
                updatedTransaction.getPaymentInformation().getId());
        assertEquals(retrievedTransaction.getTransactionId(), updatedTransaction.getTransactionId());
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
        Transaction deletedTransaction = transactionRepository
                .findTransactiontByTransactionId(transaction.getTransactionId());
        assertNull(deletedTransaction);
    }
}