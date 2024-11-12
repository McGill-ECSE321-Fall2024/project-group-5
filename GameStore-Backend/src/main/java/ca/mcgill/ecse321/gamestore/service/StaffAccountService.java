package ca.mcgill.ecse321.gamestore.service;

//import statements
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.StaffAccount;
import ca.mcgill.ecse321.gamestore.dao.StaffAccountRepository;
import ca.mcgill.ecse321.gamestore.dto.StaffAccountRequestDto;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class StaffAccountService  {
    @Autowired
    private StaffAccountRepository staffAccountRepository;

    @Transactional
    public StaffAccount createStaffAccount(StaffAccountRequestDto requestDto) {
        if (staffAccountRepository.existsStaffAccountByUsername(requestDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        StaffAccount staffAccount = new StaffAccount();
        staffAccount.setName(requestDto.getName());
        staffAccount.setUsername(requestDto.getUsername());
        staffAccount.setPassword(requestDto.getPassword()); // Assuming password is hashed elsewhere
        return staffAccountRepository.save(staffAccount);
    }

    @Transactional
    public StaffAccount getStaffAccountById(int id) {
        return staffAccountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("StaffAccount not found"));
    }

    @Transactional
    public StaffAccount getStaffAccountByUsername(String username) {
        return staffAccountRepository.findStaffAccountByUsername(username);
    }

    @Transactional
    public List<StaffAccount> getAllStaffAccounts() {
        return (List<StaffAccount>) staffAccountRepository.findAll();
    }

    @Transactional
    public void deleteStaffAccount(int id) {
        if (!staffAccountRepository.existsById(id)) {
            throw new IllegalArgumentException("StaffAccount not found");
        }
        staffAccountRepository.deleteById(id);
    }
}
