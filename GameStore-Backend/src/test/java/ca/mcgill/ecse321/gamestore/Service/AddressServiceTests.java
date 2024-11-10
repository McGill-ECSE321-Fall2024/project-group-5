package ca.mcgill.ecse321.gamestore.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void testGetAddressByIdValid() {
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
    public void testGetAddressByIdInvalid() {
        // Arrange
        int id = 1;

        when(addressRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // Act
        Address foundAddress = addressService.getAddressById(id);

        // Assert
        assertEquals(null, foundAddress);
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
        int id = 1;
        Address address = new Address();
        address.setAddress("123 Main St");
        address.setCity("Montreal");
        address.setProvince("QC");
        address.setCountry("Canada");
        address.setPostalCode("H3A 1A1");

        when(addressRepository.existsById(id)).thenReturn(true);

        // Act
        boolean deleted = addressService.deleteAddress(id);

        // Assert
        assertEquals(true, deleted);
        verify(addressRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteAddressNotFound() {
        // Arrange
        int id = 999;
        when(addressRepository.existsById(id)).thenReturn(false);

        // Act
        boolean deleted = addressService.deleteAddress(id);

        // Assert
        assertEquals(false, deleted);
    }
}
