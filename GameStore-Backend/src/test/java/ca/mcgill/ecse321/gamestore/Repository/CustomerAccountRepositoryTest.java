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
@Transactional
public class CustomerAccountRepositoryTest {

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @AfterEach
    public void clearDatabase() {
        customerAccountRepository.deleteAll();
    }

    @Test
    public void testSaveAndFindById() {
        CustomerAccount account = new CustomerAccount();
        account.setEmail("testuser@example.com"); // Corrected method name
        account.setName("Test User");

        customerAccountRepository.save(account);

        CustomerAccount foundAccount = customerAccountRepository.findById(account.getId())
            .orElse(null);

        assertNotNull(foundAccount);
        assertEquals(account.getEmailAddress(), foundAccount.getEmailAddress()); // Corrected method name
        assertEquals(account.getName(), foundAccount.getName());
    }

    @Test
    public void testFindByEmailAddress() {
        CustomerAccount account = new CustomerAccount();
        account.setEmail("findme@example.com"); // Corrected method name
        account.setName("Find Me");
        customerAccountRepository.save(account);

        CustomerAccount foundAccount = customerAccountRepository.findByEmailAddress("findme@example.com"); // Corrected query

        assertNotNull(foundAccount);
        assertEquals("findme@example.com", foundAccount.getEmailAddress()); // Corrected method name
    }

    @Test
    public void testUpdateCustomerAccount() {
        CustomerAccount account = new CustomerAccount();
        account.setEmail("update@example.com"); // Corrected method name
        account.setName("Update Me");
        customerAccountRepository.save(account);

        CustomerAccount foundAccount = customerAccountRepository.findById(account.getId()).orElse(null);
        assertNotNull(foundAccount);
        foundAccount.setName("Updated Name");
        customerAccountRepository.save(foundAccount);

        CustomerAccount updatedAccount = customerAccountRepository.findById(foundAccount.getId())
                .orElse(null);
        assertNotNull(updatedAccount);
        assertEquals("Updated Name", updatedAccount.getName());
    }

    @Test
    public void testDeleteCustomerAccount() {
        CustomerAccount account = new CustomerAccount();
        account.setEmail("delete@example.com"); // Corrected method name
        account.setName("Delete Me");
        customerAccountRepository.save(account);

        customerAccountRepository.delete(account);

        CustomerAccount deletedAccount = customerAccountRepository.findById(account.getId()).orElse(null);
        assertNull(deletedAccount);
    }
}
