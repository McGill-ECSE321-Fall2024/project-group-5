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
        assertEquals("1234 Elm Street", createdAddress.getName()); // Adjust according to the response DTO
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
        assertEquals("1234 Elm Street", address.getName()); // Adjust according to the response DTO
    }
}
