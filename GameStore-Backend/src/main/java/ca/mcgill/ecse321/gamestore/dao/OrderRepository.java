package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    
    //find orders by payment status
    Iterable<Order> findByIsPaid(boolean isPaid);
    
    //find orders by delivery status
    Iterable<Order> findByDeliveryStatus(boolean deliveryStatus);
    
    //find orders by customer account ID
    Iterable<Order> findByCustomerAccount_CustomerAccountId(int customerAccountId);
    
    //find orders by promotion code
    Iterable<Order> findByPromotionCode(String promotionCode);
    
}
