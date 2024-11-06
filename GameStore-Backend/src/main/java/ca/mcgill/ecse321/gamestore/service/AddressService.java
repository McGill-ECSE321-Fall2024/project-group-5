package ca.mcgill.ecse321.gamestore.service;

import java.util.Optional;  // Import for Optional
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.Address;
import ca.mcgill.ecse321.gamestore.dao.AddressRepository;
import jakarta.transaction.Transactional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address getAddressById(int id) {
        return addressRepository.findById(id).orElse(null);
    }

    public Iterable<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Iterable<Address> getAddressesByCity(String city) {
        return addressRepository.findByCity(city);
    }

    public Iterable<Address> getAddressesByProvince(String province) {
        return addressRepository.findByProvince(province);
    }

    public Iterable<Address> getAddressesByCountry(String country) {
        return addressRepository.findByCountry(country);
    }

    public Iterable<Address> getAddressesByPostalCode(String postalCode) {
        return addressRepository.findByPostalCode(postalCode);
    }

    public Iterable<Address> getAddressesByCustomerId(int customerId) {
        return addressRepository.findByCustomerAccount_Id(customerId);
    }

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

    public boolean deleteAddress(int id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
