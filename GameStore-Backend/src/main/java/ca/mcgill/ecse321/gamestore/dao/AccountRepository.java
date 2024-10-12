package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.Account;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    // checks by account id
    boolean existsAccountById(int id);

    Account findAccountById(int id);

    // checks by username
    boolean existsAccountByUsername(String username);

    Account findAccountByUsername(String username);

}