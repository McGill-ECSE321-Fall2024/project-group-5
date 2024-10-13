package ca.mcgill.ecse321.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.AfterEach;

import ca.mcgill.ecse321.gamestore.dao.EmployeeAccountRepository;
import ca.mcgill.ecse321.gamestore.model.*;

@SpringBootTest
class EmployeeAccountRepositoryTest {

	@Autowired
	private EmployeeAccountRepository employeeRepository;

	@AfterEach
	public void clearDatabase() {
		employeeRepository.deleteAll();
		EmployeeAccount.accountsByUsername.clear();
	}

	@Test
	public void testPersistEmployeeAccount() {

		// Create object
		EmployeeAccount employee = new EmployeeAccount();

		String username = "someEmployee";
		String passwordHash = "someHash";
		String randomPassword = "someString";

		employee.setUsername(username);
		employee.setPasswordHash(passwordHash);
		employee.setRandomPassword(randomPassword);

		// Save Object
		employee = employeeRepository.save(employee);

		// tests
		assertNotNull(employee);
		assertEquals(username, employee.getUsername());
		assertEquals(passwordHash, employee.getPasswordHash());
		assertEquals(randomPassword, employee.getRandomPassword());

		// Read object from database using ID
		employee = employeeRepository.getEmployeeAccountById(employee.getId());

		// tests
		assertNotNull(employee);
		assertEquals(username, employee.getUsername());
		assertEquals(passwordHash, employee.getPasswordHash());
		assertEquals(randomPassword, employee.getRandomPassword());

		// read object from database using username
		employee = employeeRepository.getEmployeeAccountByUsername(employee.getUsername());

		// tests
		assertNotNull(employee);
		assertEquals(username, employee.getUsername());
		assertEquals(passwordHash, employee.getPasswordHash());
		assertEquals(randomPassword, employee.getRandomPassword());
	}

	@Test
	void testDeleteEmployeeAccount() {

		// Create object
		EmployeeAccount employee = new EmployeeAccount();

		String username = "someEmployee";
		String passwordHash = "someHash";
		String randomPassword = "someString";

		employee.setUsername(username);
		employee.setPasswordHash(passwordHash);
		employee.setRandomPassword(randomPassword);

		// Save Object
		employee = employeeRepository.save(employee);

		// Delete object from database
		employeeRepository.deleteEmployeeAccountById(employee.getId());

		// Assert that database doesn't have object
		assertNull(employeeRepository.getEmployeeAccountById(employee.getId()));
		assertNull(employeeRepository.getEmployeeAccountByUsername(employee.getUsername()));

	}

	@Test
	void testModifyEmployeeAccountAttributes() {

		// Create object
		EmployeeAccount employee = new EmployeeAccount();

		String username = "someEmployee";
		String passwordHash = "someHash";
		String randomPassword = "someString";

		employee.setUsername(username);
		employee.setPasswordHash(passwordHash);
		employee.setRandomPassword(randomPassword);

		// Save Object
		employee = employeeRepository.save(employee);

		// modify attribute
		String newPasswordHash = "newHash";

		employee.setPasswordHash(newPasswordHash);
		employeeRepository.save(employee);

		// read object from database using username
		employee = employeeRepository.getEmployeeAccountById(employee.getId());

		// Assert that the modified attribute is changed
		assertNotNull(employee);
		assertEquals(username, employee.getUsername());
		assertEquals(newPasswordHash, employee.getPasswordHash());
		assertEquals(randomPassword, employee.getRandomPassword());

	}
}
