package ca.mcgill.ecse321.gamestore.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gamestore.model.StaffAccount;

public interface StaffAccountRepository extends CrudRepository<StaffAccount, Integer> {
    // checks by usernames
    boolean existsStaffAccountByUsername(String username);

    StaffAccount findStaffAccountByUsername(String username);
}
