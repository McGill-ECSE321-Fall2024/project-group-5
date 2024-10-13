package ca.mcgill.ecse321.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.AfterEach;

import ca.mcgill.ecse321.gamestore.dao.ManagerAccountRepository;
import ca.mcgill.ecse321.gamestore.model.*;

@SpringBootTest
class ManagerAccountRepositoryTest {

	@Autowired
	private ManagerAccountRepository managerRepository;

	@AfterEach
	public void clearDatabase() {
		managerRepository.deleteAll();
		ManagerAccount.accountsByUsername.clear();
	}

	@Test
	public void testPersistManagerAccount() {

		// Create object
		ManagerAccount manager = new ManagerAccount();

		String username = "admin";
		String passwordHash = "someHash";
		String randomPassword = "someString";

		manager.setUsername(username);
		manager.setPasswordHash(passwordHash);
		manager.setRandomPassword(randomPassword);

		// Save Object
		manager = managerRepository.save(manager);

		// tests
		assertNotNull(manager);
		assertNotNull(manager.getId());
		assertEquals(username, manager.getUsername());
		assertEquals(passwordHash, manager.getPasswordHash());
		assertEquals(randomPassword, manager.getRandomPassword());

		// Read object from database using ID
		manager = managerRepository.getManagerAccountByIdIsNotNull();

		// tests
		assertNotNull(manager);
		assertEquals(username, manager.getUsername());
		assertEquals(passwordHash, manager.getPasswordHash());
		assertEquals(randomPassword, manager.getRandomPassword());

		// read object from database using username
		manager = managerRepository.getManagerAccountByUsername(manager.getUsername());

		// tests
		assertNotNull(manager);
		assertEquals(username, manager.getUsername());
		assertEquals(passwordHash, manager.getPasswordHash());
		assertEquals(randomPassword, manager.getRandomPassword());
	}

	@Test
	void testDeleteManagerAccount() {

		// Create object
		ManagerAccount manager = new ManagerAccount();

		String username = "admin";
		String passwordHash = "someHash";
		String randomPassword = "someString";

		manager.setUsername(username);
		manager.setPasswordHash(passwordHash);
		manager.setRandomPassword(randomPassword);

		// Save Object
		manager = managerRepository.save(manager);

		// Delete object from database
		managerRepository.deleteManagerAccountByIdNotNull();

		// Assert that database doesn't have object
		assertNull(managerRepository.getManagerAccountByIdIsNotNull());
		assertNull(managerRepository.getManagerAccountByUsername(manager.getUsername()));

	}

	@Test
	void testModifyManagerAccountAttributes() {

		// Create object
		ManagerAccount manager = new ManagerAccount();

		String username = "someManager";
		String passwordHash = "someHash";
		String randomPassword = "someString";

		manager.setUsername(username);
		manager.setPasswordHash(passwordHash);
		manager.setRandomPassword(randomPassword);

		// Save Object
		manager = managerRepository.save(manager);

		// modifiy attribute
		String newPasswordHash = "newHash";

		manager.setPasswordHash(newPasswordHash);
		managerRepository.save(manager);

		// read object from database using username
		manager = managerRepository.getManagerAccountByIdIsNotNull();

		// Assert that the modified attribute is changed
		assertNotNull(manager);
		assertEquals(username, manager.getUsername());
		assertEquals(newPasswordHash, manager.getPasswordHash());
		assertEquals(randomPassword, manager.getRandomPassword());

	}
}