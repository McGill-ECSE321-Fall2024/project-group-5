package ca.mcgill.ecse321.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
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
    void testPersistCart() {
        // Create a new customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("password123");
        customerAccountRepository.save(someCustomer);

        // Create a new cart and associate it with the customer account
        Cart cart = new Cart();
        cart.setCustomerAccount(someCustomer);
        cart = cartRepository.save(cart);

        // Verify that the cart was saved correctly
        assertNotNull(cart);
        assertEquals(someCustomer.getUsername(), cart.getCustomerAccount().getUsername());

        // Read the cart from the database using the ID
        Optional<Cart> retrievedCart = cartRepository.findById(cart.getId());
        assertTrue(retrievedCart.isPresent());
        assertEquals(someCustomer.getUsername(), retrievedCart.get().getCustomerAccount().getUsername());
    }

    @Test
    void testFindCartByCustomerAccountId() {
        // Create a new customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("password456");
        customerAccountRepository.save(someCustomer);

        // Create a new cart and associate it with the customer account
        Cart cart = new Cart();
        cart.setCustomerAccount(someCustomer);
        cartRepository.save(cart);

        // Find carts by customer account ID
        Iterable<Cart> carts = cartRepository.findByCustomerAccount_CustomerAccountId(someCustomer.getId());
        assertTrue(carts.iterator().hasNext());

        Cart retrievedCart = carts.iterator().next();
        assertEquals(someCustomer.getUsername(), retrievedCart.getCustomerAccount().getUsername());
    }

    @Test
    void testUpdateCart() {
        // Create a new customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("agent007");
        customerAccountRepository.save(someCustomer);

        // Create a new cart and associate it with the customer account
        Cart cart = new Cart();
        cart.setCustomerAccount(someCustomer);
        cartRepository.save(cart);

        // Retrieve the cart and update the customer account association (if needed)
        Optional<Cart> retrievedCart = cartRepository.findById(cart.getId());
        assertTrue(retrievedCart.isPresent());

        CustomerAccount anotherCustomer = new CustomerAccount();
        anotherCustomer.setUsername("anotherCustomer");
        anotherCustomer.setEmail("anotherCustomer@example.com");
        anotherCustomer.setPasswordHash("newpass123");
        customerAccountRepository.save(anotherCustomer);

        Cart cartToUpdate = retrievedCart.get();
        cartToUpdate.setCustomerAccount(anotherCustomer);
        cartRepository.save(cartToUpdate);

        // Verify the cart was updated
        Optional<Cart> updatedCart = cartRepository.findById(cartToUpdate.getId());
        assertTrue(updatedCart.isPresent());
        assertEquals(anotherCustomer.getUsername(), updatedCart.get().getCustomerAccount().getUsername());
    }

    @Test
    void testDeleteCart() {
        // Create a new customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("delete123");
        customerAccountRepository.save(someCustomer);

        // Create a new cart and associate it with the customer account
        Cart cart = new Cart();
        cart.setCustomerAccount(someCustomer);
        cartRepository.save(cart);

        // Verify the cart exists
        Optional<Cart> retrievedCart = cartRepository.findById(cart.getId());
        assertTrue(retrievedCart.isPresent());

        // Delete the cart
        cartRepository.deleteById(cart.getId());

        // Verify the cart was deleted
        Optional<Cart> deletedCart = cartRepository.findById(cart.getId());
        assertTrue(deletedCart.isEmpty());
    }

    @Test
    void testFindByInvalidId() {
        // Attempt to retrieve a cart with a non-existent ID
        Optional<Cart> nonExistentCart = cartRepository.findById(999);
        assertTrue(nonExistentCart.isEmpty());
    }
}
