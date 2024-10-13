package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.Cart;

import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Integer> {

    // find all carts for a specific customer account by ID
    Iterable<Cart> findByCustomerAccount_CustomerAccountId(int customerAccountId);

    // find the cart for a specific order ID
    Cart findByOrder_OrderId(int orderId);
}
