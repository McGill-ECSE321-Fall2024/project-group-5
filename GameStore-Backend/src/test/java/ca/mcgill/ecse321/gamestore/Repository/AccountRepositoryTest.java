package ca.mcgill.ecse321.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.AfterEach;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.gamestore.dao.AccountRepository;
import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.gamestore.dao.StaffAccountRepository;
import ca.mcgill.ecse321.gamestore.model.Account;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.StaffAccount;

@SpringBootTest
@Transactional // Ensures that changes are rolled back after each test
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private StaffAccountRepository staffAccountRepository;

	@Autowired
	private CustomerAccountRepository customerAccountRepository;

	@AfterEach
	public void clearDatabase() {
		// Clear database after each test
		staffAccountRepository.deleteAll();
		customerAccountRepository.deleteAll();
	}

	@Test
	public void testFindCustomerById() {
		// Create a new customer account
		CustomerAccount account = new CustomerAccount();
		account.setUsername("french");
		account.setEmail("testuser@example.com");
		account.setName("Test User");

		// Save the customer account to the repository
		customerAccountRepository.save(account);

		// Retrieve the account by its ID
		Account foundAccount = accountRepository.findAccountById(account.getId());

		// Ensure the account was found and fields match
		assertNotNull(foundAccount);
		assertEquals(account.getId(), foundAccount.getId());
		assertEquals(account.getUsername(), foundAccount.getUsername());
	}

	@Test
	public void testFindStaffById() {
		// Create a new staff account
		StaffAccount account = new StaffAccount();
		account.setUsername("french");

		// Save the staff account to the repository
		staffAccountRepository.save(account);

		// Retrieve the account by its ID
		Account foundAccount = accountRepository.findAccountById(account.getId());

		// Ensure the account was found and fields match
		assertNotNull(foundAccount);
		assertEquals(account.getId(), foundAccount.getId());
		assertEquals(account.getUsername(), foundAccount.getUsername());
	}

	@Test
	public void testFindCustomerByUsername() {
		// Create a new customer account
		CustomerAccount account = new CustomerAccount();
		account.setUsername("french");
		account.setEmail("testuser@example.com");
		account.setName("Test User");

		// Save the customer account to the repository
		accountRepository.save(account);

		// Retrieve the account by its Username
		Account foundAccount = accountRepository.findAccountByUsername(account.getUsername());

		// Ensure the account was found and fields match
		assertNotNull(foundAccount);
		assertEquals(account.getId(), foundAccount.getId());
		assertEquals(account.getUsername(), foundAccount.getUsername());
	}

	@Test
	public void testFindStaffByUsername() {
		// Create a new staff account
		StaffAccount account = new StaffAccount();
		account.setUsername("french");

		// Save the staff account to the repository
		staffAccountRepository.save(account);

		// Retrieve the account by its Username
		Account foundAccount = accountRepository.findAccountByUsername(account.getUsername());

		// Ensure the account was found and fields match
		assertNotNull(foundAccount);
		assertEquals(account.getId(), foundAccount.getId());
		assertEquals(account.getUsername(), foundAccount.getUsername());
	}
}
