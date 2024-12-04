package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.dao.StaffAccountRepository;
import ca.mcgill.ecse321.gamestore.model.StaffAccount;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffAccountService {

    @Autowired
    private StaffAccountRepository staffAccountRepository;

    @Autowired
    private AccountService accountService;

    @Transactional
    public StaffAccount getStaffAccountById(int id) {
        StaffAccount staffAccount = staffAccountRepository.findStaffAccountById(id);
        if (staffAccount == null) {
            throw new IllegalArgumentException("No staff account associated with given id");
        }
        return staffAccount;
    }

    @Transactional
    public StaffAccount getStaffAccountByUsername(String username) {
        StaffAccount staffAccount = staffAccountRepository.findStaffAccountByUsername(username);
        if (staffAccount == null) {
            throw new IllegalArgumentException("No staff account associated with given username");
        }
        return staffAccount;
    }

    @Transactional
    public List<StaffAccount> getAllStaffAccounts() {
        return toList(staffAccountRepository.findAll());
    }

    @Transactional
    public StaffAccount deleteStaffAccount(int id) {
        StaffAccount staffAccount = staffAccountRepository.findStaffAccountById(id);
        if (staffAccount == null) {
            throw new IllegalArgumentException("No account associated with this id exists");
        }
        staffAccountRepository.deleteById(id);
        return staffAccount;
    }

    @Transactional
    public StaffAccount createStaffAccount(String username, String password) {
        if (username == null) {
            throw new IllegalArgumentException("Username must not be null");
        }
        if (username.trim().length() == 0) {
            throw new IllegalArgumentException("Username must not be empty");
        }
        // if (!checkUsernameAvailabilityStaffAccount(username)
        // || !(staffAccountRepository.findStaffAccountByUsername(username) == null)) {
        // throw new IllegalArgumentException("Username is already taken");
        // }
        if (!accountService.checkUsernameAvailability(username)) {
            throw new IllegalArgumentException("Username is already taken");
        }
        if (AccountService.isValidPassword(password) == "") {
            throw new IllegalArgumentException("Password does not meet security requirements");
        }

        String salt = AccountService.generateSalt(8);
        String hashedPassword = AccountService.hashPassword(password, salt);

        StaffAccount newStaffAccount = new StaffAccount(username, hashedPassword, salt);
        staffAccountRepository.save(newStaffAccount);
        return newStaffAccount;
    }

    @Transactional
    public StaffAccount updateStaffAccountPassword(String username, String newPassword) {
        StaffAccount staffAccount = getStaffAccountByUsername(username);
        if (!AccountService.isValidPassword(newPassword).isEmpty()) {
            throw new IllegalArgumentException(AccountService.isValidPassword(newPassword));
        }

        String newSalt = AccountService.generateSalt(8);
        String hashedPassword = AccountService.hashPassword(newPassword, newSalt);

        staffAccount.setPasswordHash(hashedPassword);
        staffAccount.setRandomPassword(newSalt);
        staffAccountRepository.save(staffAccount);
        return staffAccount;
    }

    @Transactional
    public StaffAccount authenticateWithUsername(String username, String password) {
        // check input
        if (username == null || username.trim().length() == 0 || password == null || password.trim().length() == 0
                || !AccountService.isValidPassword(password).isEmpty()) {
            throw new IllegalArgumentException("Please enter a valid email and password");
        }
        StaffAccount staffAccount = staffAccountRepository.findStaffAccountByUsername(username);
        if (staffAccount == null) {
            throw new IllegalArgumentException("There is not account associated with given username");
        }
        String salt = staffAccount.getRandomPassword();
        String hashedPassword = AccountService.hashPassword(password, salt);
        if (hashedPassword.equals(staffAccount.getPasswordHash())) {
            return staffAccount;
        } else {
            throw new IllegalArgumentException("Password and email do not match");
        }
    }

    @Transactional
    public boolean checkUsernameAvailabilityStaffAccount(String username) {
        return accountService.checkUsernameAvailability(username);

    }

    /**
     * Helper method to turn iterable into list
     * 
     * @param <T>
     * @param iterable
     * @return iterable as a list
     * @vivianeltain
     */
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}