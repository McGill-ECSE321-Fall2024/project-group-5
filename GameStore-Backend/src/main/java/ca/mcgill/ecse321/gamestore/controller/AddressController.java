package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.AddressRequestDto;
import ca.mcgill.ecse321.gamestore.dto.AddressResponseDto;
import ca.mcgill.ecse321.gamestore.model.Address;
import ca.mcgill.ecse321.gamestore.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/addresses") // Base URL for Address-related endpoints
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * POST endpoint to create a new address.
     *
     * @param addressRequestDto the request body containing address details
     * @return AddressResponseDto representing the created address
     */
    @PostMapping
    public ResponseEntity<AddressResponseDto> createAddress(@RequestBody AddressRequestDto addressRequestDto) {
        try {
            Address address = new Address();
            address.setAddress(addressRequestDto.getAddress());
            address.setCity(addressRequestDto.getCity());
            address.setProvince(addressRequestDto.getProvince());
            address.setCountry(addressRequestDto.getCountry());
            address.setPostalCode(addressRequestDto.getPostalCode());

            Address createdAddress = addressService.createAddress(address);
            return ResponseEntity.status(HttpStatus.CREATED).body(new AddressResponseDto(createdAddress));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * GET endpoint to retrieve all addresses.
     *
     * @return List of AddressResponseDto representing all addresses
     */
    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> getAllAddresses() {
        List<AddressResponseDto> addresses = StreamSupport.stream(addressService.getAllAddresses().spliterator(), false)
                .map(AddressResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addresses);
    }

    /**
     * GET endpoint to retrieve an address by its ID.
     *
     * @param id the ID of the address to retrieve
     * @return AddressResponseDto representation of the address
     */
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> getAddressById(@PathVariable("id") int id) {
        try {
            Address address = addressService.getAddressById(id);
            return ResponseEntity.ok(new AddressResponseDto(address));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * PUT endpoint to update an existing address by its ID.
     *
     * @param id the ID of the address to update
     * @param addressRequestDto the request body containing updated address details
     * @return AddressResponseDto representing the updated address
     */
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDto> updateAddress(
            @PathVariable("id") int id,
            @RequestBody AddressRequestDto addressRequestDto) {
        try {
            Address updatedAddress = new Address();
            updatedAddress.setAddress(addressRequestDto.getAddress());
            updatedAddress.setCity(addressRequestDto.getCity());
            updatedAddress.setProvince(addressRequestDto.getProvince());
            updatedAddress.setCountry(addressRequestDto.getCountry());
            updatedAddress.setPostalCode(addressRequestDto.getPostalCode());

            Address address = addressService.updateAddress(id, updatedAddress);
            return ResponseEntity.ok(new AddressResponseDto(address));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * DELETE endpoint to remove an address by its ID.
     *
     * @param id the ID of the address to delete
     * @return HTTP response with no content on success or error message on failure
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable("id") int id) {
        try {
            addressService.deleteAddress(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET endpoint to retrieve all addresses for a specific city.
     *
     * @param city the name of the city to filter addresses by
     * @return List of AddressResponseDto representing addresses in the specified city
     */
    @GetMapping("/city/{city}")
    public ResponseEntity<List<AddressResponseDto>> getAddressesByCity(@PathVariable("city") String city) {
        List<AddressResponseDto> addresses = StreamSupport.stream(addressService.getAddressesByCity(city).spliterator(), false)
                .map(AddressResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addresses);
    }
}
