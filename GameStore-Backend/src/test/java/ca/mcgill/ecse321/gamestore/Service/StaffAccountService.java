package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.dao.StaffAccountRepository;
import ca.mcgill.ecse321.gamestore.model.StaffAccount;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffAccountService {

    @Autowired
    private StaffAccountRepository staffAccountRepository;

    @Transactional
    public StaffAccount getStaffAccountById(int id) {
        return staffAccountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Staff account not found"));
    }

    @Transactional
    public StaffAccount getStaffAccountByUsername(String username) {
        StaffAccount staffAccount = staffAccountRepository.findStaffAccountByUsername(username);
        if (staffAccount == null) {
            throw new IllegalArgumentException("Staff account with username " + username + " not found");
        }
        return staffAccount;
    }

    @Transactional
    public StaffAccount createStaffAccount(String username, String password, String name) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (staffAccountRepository.existsStaffAccountByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        StaffAccount staffAccount = new StaffAccount(username, password, null, name);
        return staffAccountRepository.save(staffAccount);
    }

    @Transactional
    public void deleteStaffAccount(int id) {
        if (!staffAccountRepository.existsById(id)) {
            throw new IllegalArgumentException("Staff account not found");
        }
        staffAccountRepository.deleteById(id);
    }
}
