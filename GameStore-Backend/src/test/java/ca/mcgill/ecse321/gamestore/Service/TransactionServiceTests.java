package ca.mcgill.ecse321.gamestore.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation;
import ca.mcgill.ecse321.gamestore.model.Transaction;
import ca.mcgill.ecse321.gamestore.model.Address;
import ca.mcgill.ecse321.gamestore.service.TransactionService;
import ca.mcgill.ecse321.gamestore.dao.TransactionRepository;

@SpringBootTest
public class TransactionServiceTests {
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private TransactionService service;

    @SuppressWarnings("null")
    @Test
    public void testCreateValidTransaction() {
        // Arrange
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setEmail("bob@gmail.com");
        customerAccount.setName("bob");
        customerAccount.setUsername("bob123");
        customerAccount.setRandomPassword("aRandomPassword");
        customerAccount.setPasswordHash("thePasswordHash");

        Transaction transaction = new Transaction();
        transaction.setCustomerAccount(customerAccount);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        // Act
        Transaction createdTransaction = service.createTransaction(customerAccount);

        // Assert
        assertNotNull(createdTransaction);
        assertEquals(transaction.getAddress(), createdTransaction.getAddress());
        assertEquals(transaction.getCustomerAccount().getId(), createdTransaction.getCustomerAccount().getId());
        assertEquals(transaction.getDeliveryStatus(), createdTransaction.getDeliveryStatus());
        assertEquals(transaction.getIsPaid(), createdTransaction.getIsPaid());
        assertEquals(transaction.getPaymentInformation(), createdTransaction.getPaymentInformation());
        assertEquals(transaction.getTotalPrice(), createdTransaction.getTotalPrice());
        assertEquals(transaction.getUserAgreementCheck(), createdTransaction.getUserAgreementCheck());
        assertEquals(transaction.getTransactionId(), createdTransaction.getTransactionId());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testReadTransactionByValidId() {
        // Arrange
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setEmail("bob@gmail.com");
        customerAccount.setName("bob");
        customerAccount.setUsername("bob123");
        customerAccount.setRandomPassword("aRandomPassword");
        customerAccount.setPasswordHash("thePasswordHash");

        Transaction transaction = new Transaction();
        transaction.setCustomerAccount(customerAccount);

        when(transactionRepository.findTransactiontByTransactionId(transaction.getTransactionId()))
                .thenReturn(transaction);

        // Act
        Transaction foundTransaction = service.findTransactionById(transaction.getTransactionId());

        // Assert
        assertNotNull(foundTransaction);
        assertEquals(transaction.getAddress(), foundTransaction.getAddress());
        assertEquals(transaction.getCustomerAccount().getId(), foundTransaction.getCustomerAccount().getId());
        assertEquals(transaction.getDeliveryStatus(), foundTransaction.getDeliveryStatus());
        assertEquals(transaction.getIsPaid(), foundTransaction.getIsPaid());
        assertEquals(transaction.getPaymentInformation(), foundTransaction.getPaymentInformation());
        assertEquals(transaction.getTotalPrice(), foundTransaction.getTotalPrice());
        assertEquals(transaction.getTransactionId(), foundTransaction.getTransactionId());
        assertEquals(transaction.getUserAgreementCheck(), foundTransaction.getUserAgreementCheck());
    }

    @Test
    public void testReadTransactionByInvalidId() {
        // Arrange
        int transactionId = 42;

        when(transactionRepository.findTransactiontByTransactionId(transactionId)).thenReturn(null);

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> service.findTransactionById(transactionId));
        assertEquals("No transaction for this Id.", e.getMessage());
    }

    @Test
    public void testReadTransactionsByValidCustomerId() {
        // Arrange
        CustomerAccount customerAccount = new CustomerAccount();

        Transaction transaction1 = new Transaction();
        transaction1.setCustomerAccount(customerAccount);

        Transaction transaction2 = new Transaction();
        transaction2.setCustomerAccount(customerAccount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        when(transactionRepository.findByCustomerAccount_Id(customerAccount.getId())).thenReturn(transactions);

        // Act
        List<Transaction> foundTransactions = service.getTransactionsByCustomerId(customerAccount.getId());

        // Assert
        assertNotNull(foundTransactions);
        assertEquals(2, foundTransactions.size());
        assertEquals(customerAccount.getId(), foundTransactions.get(0).getCustomerAccount().getId());
        assertEquals(customerAccount.getId(), foundTransactions.get(1).getCustomerAccount().getId());
    }

    @Test
    public void testReadTransactionsByInvalidCustomerId() {
        // Arrange
        int customerId = 999;
        when(transactionRepository.findByCustomerAccount_Id(customerId)).thenReturn(new ArrayList<>());

        // Act
        List<Transaction> foundTransactions = service.getTransactionsByCustomerId(customerId);

        // Assert
        assertNotNull(foundTransactions);
        assertEquals(0, foundTransactions.size());
    }

    @Test
    public void testReadTransactionsByIsPaid() {
        // Arrange
        boolean isPaid = true;
        Transaction transaction1 = new Transaction();
        transaction1.setIsPaid(true);

        Transaction transaction2 = new Transaction();
        transaction2.setIsPaid(true);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        when(transactionRepository.findByIsPaid(isPaid)).thenReturn(transactions);

        // Act
        List<Transaction> foundTransactions = service.getTransactionsByIsPaid(isPaid);

        // Assert
        assertNotNull(foundTransactions);
        assertEquals(2, foundTransactions.size());
        assertEquals(isPaid, foundTransactions.get(0).getIsPaid());
        assertEquals(isPaid, foundTransactions.get(1).getIsPaid());
    }

    @Test
    public void testReadTransactionsByDeliveryStatus() {
        // Arrange
        boolean deliveryStatus = true;
        Transaction transaction1 = new Transaction();
        transaction1.setDeliveryStatus(true);

        Transaction transaction2 = new Transaction();
        transaction2.setDeliveryStatus(true);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        when(transactionRepository.findByDeliveryStatus(deliveryStatus)).thenReturn(transactions);

        // Act
        List<Transaction> foundTransactions = service.getTransactionsByDeliveryStatus(deliveryStatus);

        // Assert
        assertNotNull(foundTransactions);
        assertEquals(2, foundTransactions.size());
        assertEquals(deliveryStatus, foundTransactions.get(0).getDeliveryStatus());
        assertEquals(deliveryStatus, foundTransactions.get(1).getDeliveryStatus());
    }

    @Test
    public void testDeleteTransactionByValidId() {
        // Arrange
        Transaction transaction = new Transaction();

        when(transactionRepository.findTransactiontByTransactionId(transaction.getTransactionId()))
                .thenReturn(transaction);

        // Act
        Transaction deletedTransaction = service.deleteTransaction(transaction.getTransactionId());

        // Assert
        assertNotNull(deletedTransaction);
        assertEquals(transaction.getTransactionId(), deletedTransaction.getTransactionId());
        verify(transactionRepository, times(1)).delete(transaction);
    }

    @Test
    public void testDeleteTransactionByInvalidId() {
        // Arrange
        int transactionId = 999;
        when(transactionRepository.findTransactiontByTransactionId(transactionId)).thenReturn(null);

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> service.deleteTransaction(transactionId));
        assertEquals("Transaction not found for this Id.", e.getMessage());
    }

    @Test
    public void testUpdateTransaction() {
        // Arrange
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setEmail("bob@gmail.com");
        customerAccount.setName("bob");
        customerAccount.setUsername("bob123");
        customerAccount.setRandomPassword("aRandomPassword");
        customerAccount.setPasswordHash("thePasswordHash");

        Transaction transaction = new Transaction();
        transaction.setCustomerAccount(customerAccount);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(transactionRepository.findTransactiontByTransactionId(transaction.getTransactionId()))
                .thenReturn(transaction);

        double setTotalPrice = 32;
        boolean setIsPaid = true;
        boolean setDeliveryStatus = false;
        boolean setUserAgreementCheck = true;
        Address setAddress = new Address();
        PaymentInformation setPaymentInformation = new PaymentInformation();

        // Act
        Transaction updatedTransaction = service.updateTransaction(transaction.getTransactionId(), setTotalPrice,
                setIsPaid, setDeliveryStatus, setUserAgreementCheck, setAddress, setPaymentInformation);

        // Assert
        assertNotNull(updatedTransaction);
        assertEquals(setAddress, updatedTransaction.getAddress());
        assertEquals(transaction.getCustomerAccount().getId(), updatedTransaction.getCustomerAccount().getId());
        assertEquals(setDeliveryStatus, updatedTransaction.getDeliveryStatus());
        assertEquals(setIsPaid, updatedTransaction.getIsPaid());
        assertEquals(setPaymentInformation, updatedTransaction.getPaymentInformation());
        assertEquals(setTotalPrice, updatedTransaction.getTotalPrice());
        assertEquals(setUserAgreementCheck, updatedTransaction.getUserAgreementCheck());
        assertEquals(transaction.getTransactionId(), updatedTransaction.getTransactionId());
        verify(transactionRepository, times(1)).save(transaction);
    }
}