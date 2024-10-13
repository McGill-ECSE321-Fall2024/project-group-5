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
        // create a new customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("password123");
        customerAccountRepository.save(someCustomer);

        // create a new cart and link it to the customer
        Cart cart = new Cart();
        cart.setCustomerAccount(someCustomer);
        cart = cartRepository.save(cart);

        // check if the cart got saved properly
        assertNotNull(cart);
        assertEquals(someCustomer.getUsername(), cart.getCustomerAccount().getUsername());

        // now retrieve the cart from the db and make sure it's there
        Optional<Cart> retrievedCart = cartRepository.findById(cart.getId());
        assertTrue(retrievedCart.isPresent());
        assertEquals(someCustomer.getUsername(), retrievedCart.get().getCustomerAccount().getUsername());
    }

    @Test
    void testFindCartByCustomerAccountId() {
        // create a customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("password456");
        customerAccountRepository.save(someCustomer);

        // create a cart and associate it with the customer account
        Cart cart = new Cart();
        cart.setCustomerAccount(someCustomer);
        cartRepository.save(cart);

        // look for carts by the customer account id
        Iterable<Cart> carts = cartRepository.findByCustomerAccount_CustomerAccountId(someCustomer.getId());
        assertTrue(carts.iterator().hasNext());

        Cart retrievedCart = carts.iterator().next();
        assertEquals(someCustomer.getUsername(), retrievedCart.getCustomerAccount().getUsername());
    }

    @Test
    void testUpdateCart() {
        // create a customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("agent007");
        customerAccountRepository.save(someCustomer);

        // create a cart and link it to the customer
        Cart cart = new Cart();
        cart.setCustomerAccount(someCustomer);
        cartRepository.save(cart);

        // fetch the cart and then update it (link it to another customer)
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

        // verify the cart's been updated
        Optional<Cart> updatedCart = cartRepository.findById(cartToUpdate.getId());
        assertTrue(updatedCart.isPresent());
        assertEquals(anotherCustomer.getUsername(), updatedCart.get().getCustomerAccount().getUsername());
    }

    @Test
    void testDeleteCart() {
        // create a customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("delete123");
        customerAccountRepository.save(someCustomer);

        // create a cart and associate it with the customer
        Cart cart = new Cart();
        cart.setCustomerAccount(someCustomer);
        cartRepository.save(cart);

        // make sure the cart exists
        Optional<Cart> retrievedCart = cartRepository.findById(cart.getId());
        assertTrue(retrievedCart.isPresent());

        // delete the cart
        cartRepository.deleteById(cart.getId());

        // check if the cart was removed
        Optional<Cart> deletedCart = cartRepository.findById(cart.getId());
        assertTrue(deletedCart.isEmpty());
    }

    @Test
    void testFindByInvalidId() {
        // try fetching a cart that doesn't exist
        Optional<Cart> nonExistentCart = cartRepository.findById(999);
        assertTrue(nonExistentCart.isEmpty());
    }
}
