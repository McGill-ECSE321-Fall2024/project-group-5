package ca.mcgill.ecse321.gamestore.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gamestore.dto.AddressRequestDto;
import ca.mcgill.ecse321.gamestore.dto.AddressResponseDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AddressIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    private int validId;

    @Test
    @Order(1)
    public void testCreateValidAddress() {
        // Arrange
        AddressRequestDto request = new AddressRequestDto();
        request.setAddress("1234 Elm Street");
        request.setCity("Montreal");
        request.setProvince("Quebec");
        request.setCountry("Canada");
        request.setPostalCode("H3A 1A1");
        request.setCustomerName("Alice");

        // Act
        ResponseEntity<AddressResponseDto> response = client.postForEntity("/addresses", request,
                AddressResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        AddressResponseDto createdAddress = response.getBody();
        assertNotNull(createdAddress);
        assertEquals("1234 Elm Street", createdAddress.getAddress()); // Updated to match correct field
        assertNotNull(createdAddress.getId());
        assertTrue(createdAddress.getId() > 0, "Response should have a positive ID.");
        this.validId = createdAddress.getId();
    }

    @Test
    @Order(2)
    public void testReadAddressByValidId() {
        // Arrange
        String url = "/addresses/" + this.validId;

        // Act
        ResponseEntity<AddressResponseDto> response = client.getForEntity(url, AddressResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AddressResponseDto address = response.getBody();
        assertNotNull(address);
        assertEquals(this.validId, address.getId());
        assertEquals("1234 Elm Street", address.getAddress()); // Updated to match correct field
    }

    @Test
    @Order(3)
    public void testDeleteAddress() {
        // Act
        ResponseEntity<Void> deleteResponse = client.exchange("/addresses/" + this.validId, HttpMethod.DELETE, null,
                Void.class);

        // Assert Delete Response
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        // Attempt to fetch deleted entity
        ResponseEntity<AddressResponseDto> response = client.getForEntity("/addresses/" + this.validId,
                AddressResponseDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(4)
    public void testCreateAddressWithInvalidData() {
        // Arrange
        AddressRequestDto request = new AddressRequestDto();
        request.setAddress(""); // Empty address
        request.setCity("Montreal");
        request.setProvince("Quebec");
        request.setCountry("Canada");
        request.setPostalCode("H3A 1A1");
        request.setCustomerName("Alice");

        // Act
        ResponseEntity<AddressResponseDto> response = client.postForEntity("/addresses", request,
                AddressResponseDto.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void testGetAddressWithNonExistentId() {
        // Arrange
        int nonExistentId = 999999; // ID that doesn't exist

        // Act
        ResponseEntity<AddressResponseDto> response = client.getForEntity("/addresses/" + nonExistentId,
                AddressResponseDto.class);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void testGetAddressesByCity() {
        // Arrange
        AddressRequestDto request1 = new AddressRequestDto();
        request1.setAddress("5678 Oak Street");
        request1.setCity("Montreal");
        request1.setProvince("Quebec");
        request1.setCountry("Canada");
        request1.setPostalCode("H3B 2B2");
        request1.setCustomerName("Bob");

        AddressRequestDto request2 = new AddressRequestDto();
        request2.setAddress("910 Maple Ave");
        request2.setCity("Toronto");
        request2.setProvince("Ontario");
        request2.setCountry("Canada");
        request2.setPostalCode("M5H 3J1");
        request2.setCustomerName("Charlie");

        client.postForEntity("/addresses", request1, AddressResponseDto.class);
        client.postForEntity("/addresses", request2, AddressResponseDto.class);

        // Act
        ResponseEntity<AddressResponseDto[]> response = client.getForEntity("/addresses/city/Montreal",
                AddressResponseDto[].class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AddressResponseDto[] addresses = response.getBody();
        assertNotNull(addresses);
        assertTrue(addresses.length > 0);
        assertEquals("Montreal", addresses[0].getCity());
    }

    /*
     * @Test
     * 
     * @Order(7)
     * public void testUpdateAddress() {
     * // Arrange
     * AddressRequestDto updatedRequest = new AddressRequestDto();
     * updatedRequest.setAddress("1234 Elm Street Updated");
     * updatedRequest.setCity("Montreal");
     * updatedRequest.setProvince("Quebec");
     * updatedRequest.setCountry("Canada");
     * updatedRequest.setPostalCode("H3A 1A2");
     * 
     * // Act
     * ResponseEntity<AddressResponseDto> response = client.exchange(
     * "/addresses/" + this.validId,
     * HttpMethod.PUT,
     * new org.springframework.http.HttpEntity<>(updatedRequest),
     * AddressResponseDto.class);
     * 
     * // Assert
     * assertNotNull(response);
     * assertEquals(HttpStatus.OK, response.getStatusCode());
     * AddressResponseDto updatedAddress = response.getBody();
     * assertNotNull(updatedAddress);
     * assertEquals("1234 Elm Street Updated", updatedAddress.getAddress());
     * assertEquals("H3A 1A2", updatedAddress.getPostalCode());
     * }
     * 
     * @Test
     * 
     * @Order(8)
     * public void testUpdateAddressWithInvalidData() {
     * // Arrange
     * AddressRequestDto invalidUpdateRequest = new AddressRequestDto();
     * invalidUpdateRequest.setAddress(""); // Invalid empty address
     * invalidUpdateRequest.setCity("Montreal");
     * invalidUpdateRequest.setProvince("Quebec");
     * invalidUpdateRequest.setCountry("Canada");
     * invalidUpdateRequest.setPostalCode("H3A 1A1");
     * invalidUpdateRequest.setCustomerName("Alice");
     * 
     * // Act
     * ResponseEntity<AddressResponseDto> response = client.exchange(
     * "/addresses/" + this.validId,
     * HttpMethod.PUT,
     * new org.springframework.http.HttpEntity<>(invalidUpdateRequest),
     * AddressResponseDto.class);
     * 
     * // Assert
     * assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
     * }
     */

    @Test
    @Order(7)
    public void testListAllAddresses() {
        // Act
        ResponseEntity<AddressResponseDto[]> response = client.getForEntity("/addresses", AddressResponseDto[].class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AddressResponseDto[] addresses = response.getBody();
        assertNotNull(addresses);
        assertTrue(addresses.length > 0);
    }

    @Test
    @Order(8)
    public void testDeleteNonExistentAddress() {
        // Act
        ResponseEntity<Void> deleteResponse = client.exchange("/addresses/999999", HttpMethod.DELETE, null, Void.class);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());
    }

}
