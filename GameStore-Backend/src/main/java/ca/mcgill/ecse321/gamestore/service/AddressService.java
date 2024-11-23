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

    /**
     * Creates a new Address entry in the database.
     * 
     * @param address the Address object to be created
     * @return the created Address object
     * @throws IllegalArgumentException if any address attribute is null or if the country is not "Canada"
     */

    @Transactional
    public Address createAddress(Address address) {
        if (address == null ||
                address.getAddress() == null ||
                address.getCity() == null ||
                address.getProvince() == null ||
                address.getCountry() == null ||
                address.getPostalCode() == null) {
            throw new IllegalArgumentException("All address attributes must be non-null.");
        }

        if ("Canada" != address.getCountry()) {
            throw new IllegalArgumentException("Only addresses in Canada are supported.");
        }

        return addressRepository.save(address);
    }

     /**
     * Updates an existing Address entry.
     * 
     * @param id the ID of the Address to update
     * @param updatedAddress the Address object containing updated information
     * @return the updated Address object
     * @throws IllegalArgumentException if the Address with the given ID is not found or if any updated attribute is null
     */
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

    /**
     * Deletes an Address entry by ID.
     * 
     * @param id the ID of the Address to delete
     * @return true if the deletion was successful, otherwise false
     * @throws IllegalArgumentException if the Address with the given ID is not found
     */
    @Transactional
    public boolean deleteAddress(int id) {
        // Check if the address exists by id
        if (addressRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Address with ID " + id + " not found.");
        }

        // If address exists, delete it
        addressRepository.deleteById(id);

        // Return true to indicate the deletion was successful
        return true;
    }

    /**
     * Retrieves an Address by its unique ID.
     * 
     * @param id the ID of the Address to retrieve
     * @return the Address object with the given ID
     * @throws IllegalArgumentException if the Address with the given ID is not found
     */
    @Transactional
    public Address getAddressById(int id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address with ID " + id + " not found."));
    }

    /**
     * Retrieves all Address entries from the database.
     * 
     * @return an iterable collection of all Address objects
     */
    public Iterable<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    /**
     * Retrieves all Address entries for a specified city.
     * 
     * @param city the name of the city to filter addresses by
     * @return an iterable collection of Address objects for the specified city
     */
    public Iterable<Address> getAddressesByCity(String city) {
        return addressRepository.findByCity(city);
    }

    /**
     * Retrieves all Address entries for a specified province.
     * 
     * @param province the name of the province to filter addresses by
     * @return an iterable collection of Address objects for the specified province
     */
    public Iterable<Address> getAddressesByProvince(String province) {
        return addressRepository.findByProvince(province);
    }

    /**
     * Retrieves all Address entries for a specified country.
     * 
     * @param country the name of the country to filter addresses by
     * @return an iterable collection of Address objects for the specified country
     */
    public Iterable<Address> getAddressesByCountry(String country) {
        return addressRepository.findByCountry(country);
    }

    /**
     * Retrieves all Address entries with a specified postal code.
     * 
     * @param postalCode the postal code to filter addresses by
     * @return an iterable collection of Address objects with the specified postal code
     */
    public Iterable<Address> getAddressesByPostalCode(String postalCode) {
        return addressRepository.findByPostalCode(postalCode);
    }

    /**
     * Retrieves all Address entries for a specified customer ID.
     * 
     * @param customerId the customer ID to filter addresses by
     * @return an iterable collection of Address objects associated with the given customer ID
     */
    public Iterable<Address> getAddressesByCustomerId(int customerId) {
        return addressRepository.findByCustomerAccount_Id(customerId);
    }
}
