package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.EmployeeAccount;
import jakarta.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface EmployeeAccountRepository extends CrudRepository<EmployeeAccount, Integer> {
    EmployeeAccount getEmployeeAccountById(int id);

    Integer deleteEmployeeAccountById(int id);

    EmployeeAccount getEmployeeAccountByUsername(String username);
}