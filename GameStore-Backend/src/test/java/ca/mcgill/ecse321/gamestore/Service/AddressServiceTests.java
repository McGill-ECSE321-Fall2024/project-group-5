package ca.mcgill.ecse321.gamestore.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamestore.model.Address;
import ca.mcgill.ecse321.gamestore.service.AddressService;
import ca.mcgill.ecse321.gamestore.dao.AddressRepository;

@SpringBootTest
public class AddressServiceTests {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @Test
    public void testCreateAddress() {
        // Arrange
        Address address = new Address();
        address.setAddress("123 Main St");
        address.setCity("Montreal");
        address.setProvince("QC");
        address.setCountry("Canada");
        address.setPostalCode("H3A 1A1");

        when(addressRepository.save(any(Address.class))).thenReturn(address);

        // Act
        Address createdAddress = addressService.createAddress(address);

        // Assert
        assertNotNull(createdAddress);
        assertEquals(address.getAddress(), createdAddress.getAddress());
        assertEquals(address.getCity(), createdAddress.getCity());
        assertEquals(address.getProvince(), createdAddress.getProvince());
        assertEquals(address.getCountry(), createdAddress.getCountry());
        assertEquals(address.getPostalCode(), createdAddress.getPostalCode());
        verify(addressRepository, times(1)).save(address);
    }

    @Test
    public void testGetAddressByValidId() {
        // Arrange
        int id = 1;
        Address address = new Address();
        address.setAddress("123 Main St");
        address.setCity("Montreal");
        address.setProvince("QC");
        address.setCountry("Canada");
        address.setPostalCode("H3A 1A1");

        when(addressRepository.findById(id)).thenReturn(java.util.Optional.of(address));

        // Act
        Address foundAddress = addressService.getAddressById(id);

        // Assert
        assertNotNull(foundAddress);
        assertEquals(address.getAddress(), foundAddress.getAddress());
        assertEquals(address.getCity(), foundAddress.getCity());
        assertEquals(address.getProvince(), foundAddress.getProvince());
        assertEquals(address.getCountry(), foundAddress.getCountry());
        assertEquals(address.getPostalCode(), foundAddress.getPostalCode());
    }

    @Test
    public void testGetAddressByInvalidId() {
        // Arrange
        int id = 1;

        when(addressRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> addressService.deleteAddress(id));
        assertEquals("Address with ID 1 not found.", e.getMessage());
    }

    @Test
    public void testUpdateAddress() {
        // Arrange
        int id = 1;
        Address oldAddress = new Address();
        oldAddress.setAddress("123 Main St");
        oldAddress.setCity("Montreal");
        oldAddress.setProvince("QC");
        oldAddress.setCountry("Canada");
        oldAddress.setPostalCode("H3A 1A1");

        Address updatedAddress = new Address();
        updatedAddress.setAddress("456 Elm St");
        updatedAddress.setCity("Toronto");
        updatedAddress.setProvince("ON");
        updatedAddress.setCountry("Canada");
        updatedAddress.setPostalCode("M5G 1X1");

        when(addressRepository.findById(id)).thenReturn(java.util.Optional.of(oldAddress));
        when(addressRepository.save(any(Address.class))).thenReturn(updatedAddress);

        // Act
        Address result = addressService.updateAddress(id, updatedAddress);

        // Assert
        assertNotNull(result);
        assertEquals(updatedAddress.getAddress(), result.getAddress());
        assertEquals(updatedAddress.getCity(), result.getCity());
        assertEquals(updatedAddress.getProvince(), result.getProvince());
        assertEquals(updatedAddress.getCountry(), result.getCountry());
        assertEquals(updatedAddress.getPostalCode(), result.getPostalCode());
    }

    @Test
    public void testDeleteAddress() {
        // Arrange
        Address address = new Address();
        address.setAddress("123 Main St");
        address.setCity("Montreal");
        address.setProvince("QC");
        address.setCountry("Canada");
        address.setPostalCode("H3A 1A1");

        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));

        // Act
        boolean deleted = addressService.deleteAddress(address.getId());

        // Assert
        assertEquals(true, deleted);
        verify(addressRepository, times(1)).deleteById(address.getId());
    }

    @Test
    public void testDeleteAddressNotFound() {
        // Arrange
        int id = 999;
        when(addressRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> addressService.deleteAddress(id));
        assertEquals("Address with ID 999 not found.", e.getMessage());
    }

    @Test
    public void testCreateAddressWithNullAddress() {
        // Arrange
        Address address = null;

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> addressService.createAddress(address));
        assertEquals("All address attributes must be non-null.", e.getMessage());
    }

    @Test
    public void testCreateAddressWithMissingFields() {
        // Arrange
        Address address = new Address(); // Fields are missing

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> addressService.createAddress(address));
        assertEquals("All address attributes must be non-null.", e.getMessage());
    }

    @Test
    public void testUpdateAddressWithInvalidId() {
        // Arrange
        int id = 999; // Non-existent ID
        Address updatedAddress = new Address();
        updatedAddress.setAddress("456 Elm St");
        updatedAddress.setCity("Toronto");
        updatedAddress.setProvince("ON");
        updatedAddress.setCountry("Canada");
        updatedAddress.setPostalCode("M5G 1X1");

        when(addressRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> addressService.updateAddress(id, updatedAddress));
        assertEquals("Address with ID 999 not found.", e.getMessage());
    }

    @Test
    public void testDeleteAddressWithInvalidId() {
        // Arrange
        int id = -1; // Invalid ID
        when(addressRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> addressService.deleteAddress(id));
        assertEquals("Address with ID -1 not found.", e.getMessage());
    }

    @Test
    public void testCreateDuplicateAddress() {
        // Arrange
        Address address = new Address();
        address.setAddress("123 Main St");
        address.setCity("Montreal");
        address.setProvince("QC");
        address.setCountry("Canada");
        address.setPostalCode("H3A 1A1");

        when(addressRepository.save(any(Address.class))).thenThrow(new IllegalArgumentException("Address already exists."));

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> addressService.createAddress(address));
        assertEquals("Address already exists.", e.getMessage());
    }

    @Test
    public void testUpdateAddressWithNullFields() {
        // Arrange
        int id = 1;
        Address updatedAddress = new Address();
        // Null fields
        when(addressRepository.findById(id)).thenReturn(Optional.of(new Address()));

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> addressService.updateAddress(id, updatedAddress));
        assertEquals("All updated address attributes must be non-null.", e.getMessage());
    }
}
