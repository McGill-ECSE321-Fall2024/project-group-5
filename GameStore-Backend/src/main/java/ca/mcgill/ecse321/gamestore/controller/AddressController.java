package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.AddressRequestDto;
import ca.mcgill.ecse321.gamestore.dto.AddressResponseDto;
import ca.mcgill.ecse321.gamestore.model.Address;
import ca.mcgill.ecse321.gamestore.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/addresses") // Base URL for Address-related endpoints
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * GET endpoint to retrieve all addresses.
     *
     * @return list of AddressResponseDto representing all addresses
     */
    @GetMapping("/get")
    public ResponseEntity<List<AddressResponseDto>> getAllAddresses() {
        Iterable<Address> addresses = addressService.getAllAddresses();
        List<AddressResponseDto> addressDtos = new ArrayList<>();
        for (Address address : addresses) {
            addressDtos.add(new AddressResponseDto(address));
        }
        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }

    /**
     * GET endpoint to retrieve an address by its ID.
     *
     * @param id the ID of the address to retrieve
     * @return AddressResponseDto representation of the address
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<AddressResponseDto> getAddressById(@PathVariable("id") int id) {
        Address address = addressService.getAddressById(id);
        if (address == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AddressResponseDto addressDto = new AddressResponseDto(address);
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

    /**
     * POST endpoint to create a new address.
     *
     * @param addressRequestDto the request body containing address details
     * @return AddressResponseDto representing the created address
     */
    @PostMapping("/create")
    public ResponseEntity<?> createAddress(@RequestBody AddressRequestDto addressRequestDto) {
       try {
            Address address = new Address();
            address.setAddress(addressRequestDto.getAddress());
            address.setCity(addressRequestDto.getCity());
            address.setProvince(addressRequestDto.getProvince());
            address.setCountry(addressRequestDto.getCountry());
            address.setPostalCode(addressRequestDto.getPostalCode());
            // Save the new address
            Address createdAddress = addressService.createAddress(address);
            AddressResponseDto addressDto = new AddressResponseDto(createdAddress);
            return new ResponseEntity<>(addressDto, HttpStatus.CREATED);
       } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
       }
    }

    /**
     * PUT endpoint to update an existing address by its ID.
     *
     * @param id the ID of the address to update
     * @param addressRequestDto the request body containing updated address details
     * @return AddressResponseDto representing the updated address
     */
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateAddress(
            @PathVariable("id") int id,
            @RequestBody AddressRequestDto addressRequestDto) {
        Address updatedAddress = new Address();
        updatedAddress.setAddress(addressRequestDto.getAddress());
        updatedAddress.setCity(addressRequestDto.getCity());
        updatedAddress.setProvince(addressRequestDto.getProvince());
        updatedAddress.setCountry(addressRequestDto.getCountry());
        updatedAddress.setPostalCode(addressRequestDto.getPostalCode());

        Address address = addressService.updateAddress(id, updatedAddress);
        if (address == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AddressResponseDto addressDto = new AddressResponseDto(address);
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

    /**
     * DELETE endpoint to remove an address by its ID.
     *
     * @param id ID of the address to delete
     * @return HTTP response with no content on success or error message on failure
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable("id") int id) {
        boolean isDeleted = addressService.deleteAddress(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
