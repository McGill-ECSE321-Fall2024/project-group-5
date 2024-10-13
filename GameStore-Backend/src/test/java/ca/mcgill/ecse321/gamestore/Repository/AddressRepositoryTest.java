package ca.mcgill.ecse321.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamestore.dao.AddressRepository;
import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.gamestore.model.Address;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;

@SpringBootTest
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @AfterEach
    public void clearDatabase() {
        addressRepository.deleteAll();
        customerAccountRepository.deleteAll();
    }

    @Test
    void testPersistAddress() {
        // Create a new customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("password123");
        customerAccountRepository.save(someCustomer);

        // Create a new address and associate it with the customer account
        Address address = new Address();
        address.setAddress("123 Main St");
        address.setCity("Montreal");
        address.setProvince("Quebec");
        address.setCountry("Canada");
        address.setPostalCode("H3A1B2");
        address.setCustomerAccount(someCustomer);
        address = addressRepository.save(address);

        // Verify that the address was saved correctly
        assertNotNull(address);
        assertEquals("123 Main St", address.getAddress());
        assertEquals("Montreal", address.getCity());
        assertEquals("Quebec", address.getProvince());
        assertEquals("Canada", address.getCountry());
        assertEquals("H3A1B2", address.getPostalCode());
        assertEquals(someCustomer.getUsername(), address.getCustomerAccount().getUsername());

        // Read the address from the database using the ID
        Optional<Address> retrievedAddress = addressRepository.findById(address.getId());
        assertTrue(retrievedAddress.isPresent());
        assertEquals("123 Main St", retrievedAddress.get().getAddress());
        assertEquals("Montreal", retrievedAddress.get().getCity());
        assertEquals("Quebec", retrievedAddress.get().getProvince());
        assertEquals("Canada", retrievedAddress.get().getCountry());
        assertEquals("H3A1B2", retrievedAddress.get().getPostalCode());
    }

    @Test
    void testFindAddressByCustomerAccountId() {
        // Create a new customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("password456");
        customerAccountRepository.save(someCustomer);

        // Create a new address and associate it with the customer account
        Address address = new Address();
        address.setAddress("456 Another St");
        address.setCity("Montreal");
        address.setProvince("Quebec");
        address.setCountry("Canada");
        address.setPostalCode("H4B1A1");
        address.setCustomerAccount(someCustomer);
        addressRepository.save(address);

        // Find addresses by customer account ID
        Iterable<Address> addresses = addressRepository.findByCustomerAccount_CustomerAccountId(someCustomer.getId());
        assertTrue(addresses.iterator().hasNext());

        Address retrievedAddress = addresses.iterator().next();
        assertEquals("456 Another St", retrievedAddress.getAddress());
        assertEquals("Montreal", retrievedAddress.getCity());
    }

    @Test
    void testUpdateAddress() {
        // Create a new customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("agent007");
        customerAccountRepository.save(someCustomer);

        // Create a new address and associate it with the customer account
        Address address = new Address();
        address.setAddress("789 Spy St");
        address.setCity("London");
        address.setProvince("England");
        address.setCountry("UK");
        address.setPostalCode("W1A1AA");
        address.setCustomerAccount(someCustomer);
        addressRepository.save(address);

        // Retrieve the address and update some fields
        Optional<Address> retrievedAddress = addressRepository.findById(address.getId());
        assertTrue(retrievedAddress.isPresent());
        Address addressToUpdate = retrievedAddress.get();
        addressToUpdate.setCity("Edinburgh");
        addressToUpdate.setPostalCode("EH1 1BB");
        addressRepository.save(addressToUpdate);

        // Verify the address was updated
        Optional<Address> updatedAddress = addressRepository.findById(addressToUpdate.getId());
        assertTrue(updatedAddress.isPresent());
        assertEquals("Edinburgh", updatedAddress.get().getCity());
        assertEquals("EH1 1BB", updatedAddress.get().getPostalCode());
    }

    @Test
    void testDeleteAddress() {
        // Create a new customer account
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("delete123");
        customerAccountRepository.save(someCustomer);

        // Create a new address and associate it with the customer account
        Address address = new Address();
        address.setAddress("123 Deletion St");
        address.setCity("Montreal");
        address.setProvince("Quebec");
        address.setCountry("Canada");
        address.setPostalCode("H3Z2Y7");
        address.setCustomerAccount(someCustomer);
        addressRepository.save(address);

        // Verify the address exists
        Optional<Address> retrievedAddress = addressRepository.findById(address.getId());
        assertTrue(retrievedAddress.isPresent());

        // Delete the address
        addressRepository.deleteById(address.getId());

        // Verify the address was deleted
        Optional<Address> deletedAddress = addressRepository.findById(address.getId());
        assertTrue(deletedAddress.isEmpty());
    }

    @Test
    void testFindByInvalidId() {
        // Attempt to retrieve an address with a non-existent ID
        Optional<Address> nonExistentAddress = addressRepository.findById(999);
        assertTrue(nonExistentAddress.isEmpty());
    }
}
