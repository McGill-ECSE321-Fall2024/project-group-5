package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.ManagerAccount;

import org.springframework.data.repository.CrudRepository;

public interface ManagerAccountRepository extends CrudRepository<ManagerAccount, Integer> {
    ManagerAccount getManagerAccountByIdIsNotNull();

    Integer deleteManagerAccountByIdNotNull();

    ManagerAccount getManagerAccountByUsername(String username);
}