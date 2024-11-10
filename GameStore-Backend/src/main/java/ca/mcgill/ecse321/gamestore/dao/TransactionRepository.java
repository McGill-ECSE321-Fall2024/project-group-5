package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.Transaction;

import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    // find transaction by TransactionId

    Transaction findTransactiontByTransactionId(int transactionId);

    // find transactions by payment status
    Iterable<Transaction> findByIsPaid(boolean isPaid);

    // find transactions by delivery status
    Iterable<Transaction> findByDeliveryStatus(boolean deliveryStatus);

    // find transactions by customer account ID
    Iterable<Transaction> findByCustomerAccount_Id(int id);
}