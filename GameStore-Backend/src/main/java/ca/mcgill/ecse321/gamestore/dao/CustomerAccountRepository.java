package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import org.springframework.data.repository.CrudRepository;

public interface CustomerAccountRepository extends CrudRepository<CustomerAccount, Long> {
    // Find CustomerAccount by id
    CustomerAccount findById(int id);

    // Custom query to find a CustomerAccount by email
    CustomerAccount findByEmailAddress(String emailAddress);

    // Custom query to find a CustomerAccount by name
    CustomerAccount findByUsername(String username);

}
