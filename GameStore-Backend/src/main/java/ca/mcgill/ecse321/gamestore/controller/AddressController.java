package ca.mcgill.ecse321.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gamestore.dto.AddressRequestDto;
import ca.mcgill.ecse321.gamestore.dto.AddressResponseDto;
import ca.mcgill.ecse321.gamestore.model.Address;
import ca.mcgill.ecse321.gamestore.service.AddressService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/addresses")  // Base URL for Address-related endpoints
public class AddressController {

    @Autowired
    private AddressService addressService;

    // Get all addresses
    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> getAllAddresses() {
        Iterable<Address> addresses = addressService.getAllAddresses();
        List<AddressResponseDto> addressDtos = new ArrayList<>();
        for (Address address : addresses) {
            addressDtos.add(new AddressResponseDto(address));
        }
        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }

    // Get address by ID
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> getAddressById(@PathVariable("id") int id) {
        Address address = addressService.getAddressById(id);
        if (address == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AddressResponseDto addressDto = new AddressResponseDto(address);
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

    // Create a new address
    @PostMapping
    public ResponseEntity<AddressResponseDto> createAddress(@RequestBody AddressRequestDto addressRequestDto) {
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
    }

    // Update an existing address
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDto> updateAddress(
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

    // Delete an address
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable("id") int id) {
        boolean isDeleted = addressService.deleteAddress(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
