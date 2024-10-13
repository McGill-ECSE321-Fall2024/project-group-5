package ca.mcgill.ecse321.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.AfterEach;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;

@SpringBootTest
@Transactional // Ensures that changes are rolled back after each test
public class CustomerAccountRepositoryTest {

	@Autowired
	private CustomerAccountRepository customerAccountRepository;

	@AfterEach
	public void clearDatabase() {
		// Clear database after each test
		customerAccountRepository.deleteAll();
	}

	@Test
	public void testSaveAndFindById() {
		// Create a new customer account
		CustomerAccount account = new CustomerAccount();
		account.setEmail("testuser@example.com");
		account.setName("Test User");

		// Save the customer account to the repository
		customerAccountRepository.save(account);

		// Retrieve the account by its ID
		CustomerAccount foundAccount = customerAccountRepository.findById(account.getId());

		// Ensure the account was found and fields match
		assertNotNull(foundAccount);
		assertEquals(account.getEmailAddress(), foundAccount.getEmailAddress());
		assertEquals(account.getName(), foundAccount.getName());
	}

	@Test
	public void testFindByEmailAddress() {
		// Create and save a customer account
		CustomerAccount account = new CustomerAccount();
		account.setEmail("findme@example.com");
		account.setName("Find Me");
		customerAccountRepository.save(account);

		// Find the customer account by email address
		CustomerAccount foundAccount = customerAccountRepository.findByEmailAddress("findme@example.com");

		// Ensure the account was found and the email matches
		assertNotNull(foundAccount);
		assertEquals("findme@example.com", foundAccount.getEmailAddress());
	}

	@Test
	public void testUpdateCustomerAccount() {
		// Create and save a customer account
		CustomerAccount account = new CustomerAccount();
		account.setEmail("update@example.com");
		account.setName("Update Me");
		customerAccountRepository.save(account);

		// Retrieve the account, update the name, and save it again
		CustomerAccount foundAccount = customerAccountRepository.findById(account.getId());
		assertNotNull(foundAccount);
		foundAccount.setName("Updated Name");
		customerAccountRepository.save(foundAccount);

		// Retrieve the updated account and verify the name change
		CustomerAccount updatedAccount = customerAccountRepository.findById(foundAccount.getId());
		assertNotNull(updatedAccount);
		assertEquals("Updated Name", updatedAccount.getName());
	}

	@Test
	public void testDeleteCustomerAccount() {
		// Create and save a customer account
		CustomerAccount account = new CustomerAccount();
		account.setEmail("delete@example.com");
		account.setName("Delete Me");
		customerAccountRepository.save(account);

		// Delete the account
		customerAccountRepository.delete(account);

		// Ensure the account is no longer in the repository
		CustomerAccount deletedAccount = customerAccountRepository.findById(account.getId());
		assertNull(deletedAccount);
	}
}
