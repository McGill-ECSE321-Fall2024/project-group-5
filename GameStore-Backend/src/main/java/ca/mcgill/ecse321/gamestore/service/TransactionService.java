package ca.mcgill.ecse321.gamestore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.Address;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation;
import ca.mcgill.ecse321.gamestore.model.Transaction;
import ca.mcgill.ecse321.gamestore.dao.TransactionRepository;
import jakarta.transaction.Transactional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Creates a new transaction for the given customer account.
     * 
     * @param customerAccount - the customer account associated with the transaction
     * @return the created transaction
     * @throws IllegalArgumentException if the customer account is null
     */
    @Transactional
    public Transaction createTransaction(CustomerAccount customerAccount) {
        if (customerAccount == null) {
            throw new IllegalArgumentException("Account is null.");
        }

        Transaction transactionToSave = new Transaction();
        transactionToSave.setCustomerAccount(customerAccount);
        transactionToSave.setUserAgreementCheck(false);
        transactionToSave.setDeliveryStatus(false);
        transactionToSave.setIsPaid(false);
        transactionToSave.setTotalPrice(0);

        return transactionRepository.save(transactionToSave);
    }

    /**
     * Finds a transaction by its ID.
     * 
     * @param id - the ID of the transaction
     * @return the found transaction
     * @throws IllegalArgumentException if no transaction is found with the given ID
     */
    @Transactional
    public Transaction findTransactionById(int id) {
        Transaction transaction = transactionRepository.findTransactiontByTransactionId(id);
        if (transaction == null) {
            throw new IllegalArgumentException("No transaction for this Id.");
        }
        return transaction;
    }

    /**
     * Retrieves all transactions associated with a specific customer.
     * 
     * @param customerId - the ID of the customer
     * @return a list of transactions associated with the given customer ID
     */
    @Transactional
    public List<Transaction> getTransactionsByCustomerId(int customerId) {
        List<Transaction> transactions = new ArrayList<>();
        transactionRepository.findByCustomerAccount_Id(customerId).forEach(transactions::add);
        return transactions;
    }

    /**
     * Retrieves all transactions based on whether they are paid.
     * 
     * @param isPaid - whether to retrieve paid or unpaid transactions
     * @return a list of transactions matching the paid status
     */
    @Transactional
    public List<Transaction> getTransactionsByIsPaid(boolean isPaid) {
        List<Transaction> transactions = new ArrayList<>();
        transactionRepository.findByIsPaid(isPaid).forEach(transactions::add);
        return transactions;
    }

    /**
     * Retrieves all transactions based on their delivery status.
     * 
     * @param deliveryStatus - whether to retrieve transactions based on delivery
     *                       status (delivered or not)
     * @return a list of transactions matching the delivery status
     */
    @Transactional
    public List<Transaction> getTransactionsByDeliveryStatus(boolean deliveryStatus) {
        List<Transaction> transactions = new ArrayList<>();
        transactionRepository.findByDeliveryStatus(deliveryStatus).forEach(transactions::add);
        return transactions;
    }

    /**
     * Deletes a transaction by its ID.
     * 
     * @param id - the ID of the transaction to delete
     * @return the deleted transaction
     * @throws IllegalArgumentException if no transaction is found with the given ID
     */
    @Transactional
    public Transaction deleteTransaction(int id) {
        Transaction transaction = transactionRepository.findTransactiontByTransactionId(id);
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction not found for this Id.");
        }

        transactionRepository.delete(transaction);
        return transaction;
    }

    /**
     * Updates an existing transaction with new values.
     * 
     * @param id                    - the ID of the transaction to update
     * @param setTotalPrice         - the new total price for the transaction
     * @param setIsPaid             - the new payment status of the transaction
     *                              (paid or not)
     * @param setDeliveryStatus     - the new delivery status of the transaction
     * @param setUserAgreementCheck - the new user agreement status
     * @param setAddress            - the new address associated with the
     *                              transaction (optional)
     * @param setPaymentInformation - the new payment information (optional)
     * @return the updated transaction
     * @throws IllegalArgumentException if no transaction is found with the given ID
     */
    @Transactional
    public Transaction updateTransaction(int id, double setTotalPrice, boolean setIsPaid, boolean setDeliveryStatus,
            boolean setUserAgreementCheck, Address setAddress, PaymentInformation setPaymentInformation) {

        Transaction transaction = transactionRepository.findTransactiontByTransactionId(id);
        if (transaction == null) {
            throw new IllegalArgumentException("No transaction for this id.");
        }

        transaction.setTotalPrice(setTotalPrice);
        transaction.setIsPaid(setIsPaid);
        transaction.setDeliveryStatus(setDeliveryStatus);
        transaction.setUserAgreementCheck(setUserAgreementCheck);
        if (setAddress != null) {
            transaction.setAddress(setAddress);
        }
        if (setPaymentInformation != null) {
            transaction.setPaymentInformation(setPaymentInformation);
        }

        return transactionRepository.save(transaction);
    }
}