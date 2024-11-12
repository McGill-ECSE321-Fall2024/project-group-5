package ca.mcgill.ecse321.gamestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.gamestore.model.Address;
import ca.mcgill.ecse321.gamestore.dao.AddressRepository;
import jakarta.transaction.Transactional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    // Creates a new Address entry in the database
    public Address createAddress(Address address) {
        if (address == null ||
            address.getAddress() == null ||
            address.getCity() == null ||
            address.getProvince() == null ||
            address.getCountry() == null ||
            address.getPostalCode() == null) {
            throw new IllegalArgumentException("All address attributes must be non-null.");
        }
        
        if (!"CANADA".equalsIgnoreCase(address.getCountry())) {
            throw new IllegalArgumentException("Only addresses in Canada are supported.");
        }

        return addressRepository.save(address);
    }

    // Retrieves an Address by its unique ID, or throws an error if not found
    public Address getAddressById(int id) {
        return addressRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Address with ID " + id + " not found."));
    }

    // Retrieves all Address entries
    public Iterable<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    // Retrieves all Address entries in a specified city
    public Iterable<Address> getAddressesByCity(String city) {
        return addressRepository.findByCity(city);
    }

    // Retrieves all Address entries in a specified province
    public Iterable<Address> getAddressesByProvince(String province) {
        return addressRepository.findByProvince(province);
    }

    // Retrieves all Address entries in a specified country
    public Iterable<Address> getAddressesByCountry(String country) {
        return addressRepository.findByCountry(country);
    }

    // Retrieves all Address entries with a specified postal code
    public Iterable<Address> getAddressesByPostalCode(String postalCode) {
        return addressRepository.findByPostalCode(postalCode);
    }

    // Retrieves all Address entries for a specific customer ID
    public Iterable<Address> getAddressesByCustomerId(int customerId) {
        return addressRepository.findByCustomerAccount_Id(customerId);
    }

    // Updates an existing Address entry if present, or throws an error if not found
    @Transactional
    public Address updateAddress(int id, Address updatedAddress) {
        Address existingAddress = addressRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Address with ID " + id + " not found."));

        if (updatedAddress == null ||
            updatedAddress.getAddress() == null ||
            updatedAddress.getCity() == null ||
            updatedAddress.getProvince() == null ||
            updatedAddress.getCountry() == null ||
            updatedAddress.getPostalCode() == null) {
            throw new IllegalArgumentException("All updated address attributes must be non-null.");
        }

        existingAddress.setAddress(updatedAddress.getAddress());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setProvince(updatedAddress.getProvince());
        existingAddress.setCountry(updatedAddress.getCountry());
        existingAddress.setPostalCode(updatedAddress.getPostalCode());

        return addressRepository.save(existingAddress);
    }

    // Deletes an Address entry by ID and returns the deleted object, throws an error if not found
    @Transactional
    public Address deleteAddress(int id) {
        Address address = addressRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Address with ID " + id + " not found."));
        
        addressRepository.deleteById(id);
        return address;
    }
}
