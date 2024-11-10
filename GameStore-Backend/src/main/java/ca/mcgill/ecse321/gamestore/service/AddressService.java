package ca.mcgill.ecse321.gamestore.service;

import java.util.Optional;
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
        return addressRepository.save(address);
    }

    // Retrieves an Address by its unique ID, or null if not found
    public Address getAddressById(int id) {
        return addressRepository.findById(id).orElse(null);
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

    // Updates an existing Address entry if present, or returns null if not found
    @Transactional
    public Address updateAddress(int id, Address updatedAddress) {
        Optional<Address> existingAddress = addressRepository.findById(id);
        if (existingAddress.isPresent()) {
            Address address = existingAddress.get();
            address.setAddress(updatedAddress.getAddress());
            address.setCity(updatedAddress.getCity());
            address.setProvince(updatedAddress.getProvince());
            address.setCountry(updatedAddress.getCountry());
            address.setPostalCode(updatedAddress.getPostalCode());
            return addressRepository.save(address);
        }
        return null;
    }

    // Deletes an Address entry by ID and returns true if deletion was successful
    @Transactional
    public boolean deleteAddress(int id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            return true;
        }
        return false;
    }
}