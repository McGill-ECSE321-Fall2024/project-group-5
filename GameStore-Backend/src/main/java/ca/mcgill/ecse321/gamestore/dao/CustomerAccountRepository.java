package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;


public interface CustomerAccountRepository extends CrudRepository<CustomerAccount, Long> {
    // Find CustomerAccount by id
    Optional<CustomerAccount> findById(Integer id);

    // Custom query to find a CustomerAccount by email
    CustomerAccount findByEmailAddress(String emailAddress);

    boolean existsByEmailAddress(String emailAddress);

    // Custom query to find a CustomerAccount by name
    CustomerAccount findByUsername(String username);

}
