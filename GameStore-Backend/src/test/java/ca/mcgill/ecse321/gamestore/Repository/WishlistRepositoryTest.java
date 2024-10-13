package ca.mcgill.ecse321.gamestore.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.AfterEach;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.gamestore.model.Wishlist;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;

@SpringBootTest
@Transactional
public class WishlistRepositoryTest {

	@Autowired
	private WishlistRepository wishlistRepository;

	@Autowired
	private CustomerAccountRepository customerAccountRepository;

	@AfterEach
	public void clearDatabase() {
		wishlistRepository.deleteAll();
		customerAccountRepository.deleteAll();
	}

	@Test
	public void testSaveAndFindById() {
		// Create a new CustomerAccount and save it
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setEmailAddress("testuser@example.com");
		customerAccount.setName("Test User");
		customerAccountRepository.save(customerAccount);

		// Create a new Wishlist associated with the CustomerAccount
		Wishlist wishlist = new Wishlist(customerAccount);
		wishlistRepository.save(wishlist);

		// Retrieve the wishlist by its ID
		Wishlist foundWishlist = wishlistRepository.findById(wishlist.getId()).orElse(null);

		// Ensure the wishlist was found and is linked to the correct customer
		assertNotNull(foundWishlist);
		assertEquals(customerAccount.getId(), foundWishlist.getCustomerAccount().getId());
	}

	@Test
	public void testFindByCustomerAccount() {
		// Create and save a CustomerAccount
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setEmailAddress("findme@example.com");
		customerAccount.setName("Find Me");
		customerAccountRepository.save(customerAccount);

		// Create and save a Wishlist for the CustomerAccount
		Wishlist wishlist = new Wishlist(customerAccount);
		wishlistRepository.save(wishlist);

		// Find the wishlist by the CustomerAccount
		Wishlist foundWishlist = wishlistRepository.findByCustomerAccount(customerAccount);

		// Ensure the wishlist is found and linked to the correct customer
		assertNotNull(foundWishlist);
		assertEquals(customerAccount.getId(), foundWishlist.getCustomerAccount().getId());
	}

	@Test
	public void testUpdateWishlist() {
		// Create and save a CustomerAccount
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setEmailAddress("update@example.com");
		customerAccount.setName("Update Me");
		customerAccountRepository.save(customerAccount);

		// Create and save a Wishlist for the CustomerAccount
		Wishlist wishlist = new Wishlist(customerAccount);
		wishlistRepository.save(wishlist);

		// Retrieve the wishlist, change the customer account name, and save it again
		Wishlist foundWishlist = wishlistRepository.findById(wishlist.getId()).orElse(null);
		assertNotNull(foundWishlist);
		foundWishlist.getCustomerAccount().setName("Updated Customer");
		customerAccountRepository.save(foundWishlist.getCustomerAccount());

		// Retrieve the updated Wishlist and verify the name change
		Wishlist updatedWishlist = wishlistRepository.findById(foundWishlist.getId()).orElse(null);
		assertNotNull(updatedWishlist);
		assertEquals("Updated Customer", updatedWishlist.getCustomerAccount().getName());
	}

	@Test
	public void testDeleteWishlist() {
		// Create and save a CustomerAccount
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setEmailAddress("delete@example.com");
		customerAccount.setName("Delete Me");
		customerAccountRepository.save(customerAccount);

		// Create and save a Wishlist for the CustomerAccount
		Wishlist wishlist = new Wishlist(customerAccount);
		wishlistRepository.save(wishlist);

		// Delete the wishlist
		wishlistRepository.delete(wishlist);

		// Ensure the wishlist is no longer in the repository
		Wishlist deletedWishlist = wishlistRepository.findById(wishlist.getId()).orElse(null);
		assertNull(deletedWishlist);
	}
}
