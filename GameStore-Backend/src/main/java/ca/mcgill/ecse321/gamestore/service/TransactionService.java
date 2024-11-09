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

    @Transactional
    public Transaction findTransactionById(int id) {
        Transaction transaction = transactionRepository.findTransactiontByTransactionId(id);
        if (transaction == null) {
            throw new IllegalArgumentException("No transaction for this Id.");
        }
        return transaction;
    }

    @Transactional
    public List<Transaction> getTransactionsByCustomerId(int customerId) {
        List<Transaction> transactions = new ArrayList<>();
        transactionRepository.findByCustomerAccount_Id(customerId).forEach(transactions::add);
        if (transactions == null || transactions.isEmpty()) {
            throw new IllegalArgumentException("No transactions associated with this customer.");
        }
        return transactions;
    }

    @Transactional
    public List<Transaction> getTransactionsByIsPaid(boolean isPaid) {
        List<Transaction> transactions = new ArrayList<>();
        transactionRepository.findByIsPaid(isPaid).forEach(transactions::add);
        if (transactions == null || transactions.isEmpty()) {
            throw new IllegalArgumentException("No unpaid transactions.");
        }
        return transactions;
    }

    @Transactional
    public List<Transaction> getTransactionsByDeliveryStatus(boolean deliveryStatus) {
        List<Transaction> transactions = new ArrayList<>();
        transactionRepository.findByDeliveryStatus(deliveryStatus).forEach(transactions::add);
        if (transactions == null || transactions.isEmpty()) {
            throw new IllegalArgumentException("No undelivered transactions.");
        }
        return transactions;
    }

    @Transactional
    public Transaction deletedTransaction(int id) {
        Transaction transaction = transactionRepository.findTransactiontByTransactionId(id);
        if (transaction == null) {
            throw new IllegalArgumentException("No transaction for this id.");
        }

        transactionRepository.delete(transaction);
        return transaction;
    }

    @Transactional
    public Transaction updateTransaction(int id, double setTotalPrice, boolean setIsPaid, boolean setDeliverStatus,
            boolean setUserAgreementCheck, Address setAddress, PaymentInformation setPaymentInformation) {

        Transaction transaction = transactionRepository.findTransactiontByTransactionId(id);
        if (transaction == null) {
            throw new IllegalArgumentException("No transaction for this id.");
        }

        transaction.setTotalPrice(setTotalPrice);
        transaction.setIsPaid(setIsPaid);
        transaction.setDeliveryStatus(setUserAgreementCheck);
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