package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Integer> {

    //find all addresses by city
    Iterable<Address> findByCity(String city);

    //find all addresses by province
    Iterable<Address> findByProvince(String province);

    //find all addresses by country
    Iterable<Address> findByCountry(String country);

    //find all addresses by postal code
    Iterable<Address> findByPostalCode(String postalCode);

    //find all addresses for a specific customer account by ID
    Iterable<Address> findByCustomerAccount_CustomerAccountId(int customerAccountId);
}
