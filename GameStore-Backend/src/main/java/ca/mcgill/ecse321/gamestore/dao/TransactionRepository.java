package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.Transaction;

import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    //find orders by payment status
    Iterable<Transaction> findByIsPaid(boolean isPaid);
    
    //find orders by delivery status
    Iterable<Transaction> findByDeliveryStatus(boolean deliveryStatus);
    
    //find orders by customer account ID
    Iterable<Transaction> findByCustomerAccount_CustomerAccountId(int customerAccountId);
    
    //find orders by promotion code
    Iterable<Transaction> findByPromotionCode(String promotionCode);
}