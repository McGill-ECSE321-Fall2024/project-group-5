package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Long> {

    // Custom query to find a CustomerAccount by email
    CustomerAccount findByEmailAddress(String emailAddress);

    // Custom query to find a CustomerAccount by name
    CustomerAccount findByName(String name);
}
