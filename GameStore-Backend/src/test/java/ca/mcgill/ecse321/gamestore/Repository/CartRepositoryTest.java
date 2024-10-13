/***package ca.mcgill.ecse321.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.AfterEach;

import ca.mcgill.ecse321.gamestore.model.*;

@SpringBootTest
class CartRepositoryTest {

	@Test
	void contextLoads() {
		assertEquals(1, 2);
		assertEquals(1, 1);
	}
}
***/
package ca.mcgill.ecse321.gamestore.repository;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamestore.dao.CartRepository;
import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.gamestore.model.Cart;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;

@SpringBootTest
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @AfterEach
    public void clearDatabase() {
        cartRepository.deleteAll();
        customerAccountRepository.deleteAll();
    }

    @Test
    void testCreateCart() {
        // Create a new customer account
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setUsername("john_doe");
        customerAccount.setEmail("john.doe@example.com");
        customerAccount.setPasswordHash("password123");
        customerAccountRepository.save(customerAccount);

        // Create a new cart and associate it with the customer account
        Cart cart = new Cart();
        cart.setCustomerAccount(customerAccount);
        cartRepository.save(cart);

        // Retrieve the cart and verify it was saved correctly
        Optional<Cart> retrievedCart = cartRepository.findById(cart.getId());
        assertTrue(retrievedCart.isPresent());
        assertNotNull(retrievedCart.get().getCustomerAccount());
        assertEquals(customerAccount.getUsername(), retrievedCart.get().getCustomerAccount().getUsername());
    }

    @Test
    void testFindCartByCustomerAccountId() {
        // Create a new customer account
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setUsername("jane_doe");
        customerAccount.setEmail("jane.doe@example.com");
        customerAccount.setPasswordHash("password456");
        customerAccountRepository.save(customerAccount);

        // Create a new cart and associate it with the customer account
        Cart cart = new Cart();
        cart.setCustomerAccount(customerAccount);
        cartRepository.save(cart);

        // Find the cart by customer account ID
        Iterable<Cart> carts = cartRepository.findByCustomerAccount_CustomerAccountId(customerAccount.getId());
        assertTrue(carts.iterator().hasNext());
        assertEquals(customerAccount.getUsername(), carts.iterator().next().getCustomerAccount().getUsername());
    }

    @Test
    void testUpdateCart() {
        // Create a new customer account
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setUsername("james_bond");
        customerAccount.setEmail("james.bond@example.com");
        customerAccount.setPasswordHash("agent007");
        customerAccountRepository.save(customerAccount);

        // Create a new cart
        Cart cart = new Cart();
        cart.setCustomerAccount(customerAccount);
        cartRepository.save(cart);

        // Retrieve the cart and update some details
        Optional<Cart> retrievedCart = cartRepository.findById(cart.getId());
        assertTrue(retrievedCart.isPresent());
        Cart cartToUpdate = retrievedCart.get();
        cartToUpdate.setId(12345); // Example of setting the ID to a new value
        cartRepository.save(cartToUpdate);

        // Verify the cart was updated
        Optional<Cart> updatedCart = cartRepository.findById(cartToUpdate.getId());
        assertTrue(updatedCart.isPresent());
        assertEquals(12345, updatedCart.get().getId());
    }

    @Test
    void testDeleteCart() {
        // Create a new customer account
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setUsername("deletion_test_user");
        customerAccount.setEmail("deletion_test@example.com");
        customerAccount.setPasswordHash("deletethis");
        customerAccountRepository.save(customerAccount);

        // Create a new cart and associate it with the customer account
        Cart cart = new Cart();
        cart.setCustomerAccount(customerAccount);
        cartRepository.save(cart);

        // Verify the cart exists
        Optional<Cart> retrievedCart = cartRepository.findById(cart.getId());
        assertTrue(retrievedCart.isPresent());

        // Delete the cart
        cartRepository.deleteById(cart.getId());

        // Verify the cart no longer exists
        Optional<Cart> deletedCart = cartRepository.findById(cart.getId());
        assertTrue(deletedCart.isEmpty());
    }

    @Test
    void testFindByInvalidId() {
        // Try to find a cart that does not exist
        Optional<Cart> nonExistentCart = cartRepository.findById(999);
        assertTrue(nonExistentCart.isEmpty());
    }

    @Test
    void testFindByOrderId() {
        // Assuming you have an Order repository and you are associating an order with a cart in your application
        // This is a placeholder test for finding by order ID if it exists
    }
}
