package ca.mcgill.ecse321.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.AfterEach;

import ca.mcgill.ecse321.gamestore.dao.StaffAccountRepository;
import ca.mcgill.ecse321.gamestore.model.*;

@SpringBootTest
class StaffAccountRepositoryTest {

	@Autowired
	private StaffAccountRepository staffRepository;

	@AfterEach
	public void clearDatabase() {
		staffRepository.deleteAll();
		StaffAccount.accountsByUsername.clear();
	}

	@Test
	public void testPersistStaffAccount() {

		// Create object
		StaffAccount staff = new StaffAccount();

		String username = "someStaff";
		String passwordHash = "someHash";
		String randomPassword = "someString";

		staff.setUsername(username);
		staff.setPasswordHash(passwordHash);
		staff.setRandomPassword(randomPassword);

		// Save Object
		staff = staffRepository.save(staff);

		// tests
		assertNotNull(staff);
		assertEquals(username, staff.getUsername());
		assertEquals(passwordHash, staff.getPasswordHash());
		assertEquals(randomPassword, staff.getRandomPassword());

		// Read object from database using ID
		staff = staffRepository.findStaffAccountById(staff.getId());

		// tests
		assertNotNull(staff);
		assertEquals(username, staff.getUsername());
		assertEquals(passwordHash, staff.getPasswordHash());
		assertEquals(randomPassword, staff.getRandomPassword());

		// read object from database using username
		staff = staffRepository.findStaffAccountByUsername(staff.getUsername());

		// tests
		assertNotNull(staff);
		assertEquals(username, staff.getUsername());
		assertEquals(passwordHash, staff.getPasswordHash());
		assertEquals(randomPassword, staff.getRandomPassword());
	}

	@Test
	void testDeleteStaffAccount() {

		// Create object
		StaffAccount staff = new StaffAccount();

		String username = "someStaff";
		String passwordHash = "someHash";
		String randomPassword = "someString";

		staff.setUsername(username);
		staff.setPasswordHash(passwordHash);
		staff.setRandomPassword(randomPassword);

		// Save Object
		staff = staffRepository.save(staff);

		// Delete object from database
		staffRepository.delete(staff);

		// Assert that database doesn't have object
		assertNull(staffRepository.findStaffAccountById(staff.getId()));
		assertNull(staffRepository.findStaffAccountByUsername(staff.getUsername()));

	}

	@Test
	void testModifyStaffAccountAttributes() {

		// Create object
		StaffAccount staff = new StaffAccount();

		String username = "someStaff";
		String passwordHash = "someHash";
		String randomPassword = "someString";

		staff.setUsername(username);
		staff.setPasswordHash(passwordHash);
		staff.setRandomPassword(randomPassword);

		// Save Object
		staff = staffRepository.save(staff);

		// modify attribute
		String newPasswordHash = "newHash";

		staff.setPasswordHash(newPasswordHash);
		staffRepository.save(staff);

		// read object from database using username
		staff = staffRepository.findStaffAccountById(staff.getId());

		// Assert that the modified attribute is changed
		assertNotNull(staff);
		assertEquals(username, staff.getUsername());
		assertEquals(newPasswordHash, staff.getPasswordHash());
		assertEquals(randomPassword, staff.getRandomPassword());

	}
}
