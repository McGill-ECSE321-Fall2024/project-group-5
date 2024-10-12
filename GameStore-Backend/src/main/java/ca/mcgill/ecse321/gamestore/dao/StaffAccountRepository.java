package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.StaffAccount;

import org.springframework.data.repository.CrudRepository;

public interface StaffAccountRepository extends CrudRepository<StaffAccount, Integer> {
    // checks by account id
    boolean existsStaffAccountById(int id);

    StaffAccount findStaffAccountById(int id);

    // checks by username
    boolean existsStaffAccountByUsername(String username);

    StaffAccount findStaffAccountByUsername(String username);
}