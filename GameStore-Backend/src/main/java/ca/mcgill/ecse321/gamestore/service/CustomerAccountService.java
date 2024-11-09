package ca.mcgill.ecse321.gamestore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.Account;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import jakarta.transaction.Transactional;

@Service
public class CustomerAccountService {
    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Autowired
    private AccountService accountService;

    public CustomerAccount getCustomerAccountByID(int id) throws Exception {
        CustomerAccount customerAccount = customerAccountRepository.findById(id);
        if (customerAccount == null) {
            throw new Exception("No account associated with this id exists");
        }
        return customerAccount;
    }

    public CustomerAccount getCustomerAccountByEmail(String email) throws Exception {
        CustomerAccount customerAccount = customerAccountRepository.findByEmailAddress(email);
        if (customerAccount == null) {
            throw new Exception("No account associated with this email exists");
        }
        return customerAccount;
    }

    public CustomerAccount getCustomerAccountByUsername(String username) throws Exception {
        CustomerAccount customerAccount = customerAccountRepository.findByUsername(username);
        if (customerAccount == null) {
            throw new Exception("No account associated with this username exists");
        }
        return customerAccount;
    }

    public List<CustomerAccount> getAllCustomerAccounts() {
        return toList(customerAccountRepository.findAll());
    }

    public CustomerAccount deleteAccount(int id) throws Exception {
        CustomerAccount customerAccount = customerAccountRepository.findById(id);
        if (customerAccount == null) {
            throw new Exception("No account associated with this id exists");
        }
        customerAccountRepository.delete(customerAccount);
        return customerAccount;
    }

    private static String isValidEmail(String email) {
        if (email == null) {
            return "Email cannot be null";
        }

        if (email.isEmpty()) {
            return "Email cannot be empty";
        }

        int atIndex = email.indexOf('@');
        // checks that there is an "@" in the email that is not at the start or
        // end of the email string
        if (atIndex == -1 || atIndex == 0 || atIndex == email.length() - 1) {
            return "Email is not valid";
        }
        String localPart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex + 1);

        // if localPart or domainPart is empty return
        // false or if domain part contains two dots in a row
        if (localPart.isEmpty() || domainPart.isEmpty() || domainPart.contains("..")) {
            return "Email is not valid";
        }
        // checks that there is an "." in the domain part of the email and that is
        // not in the start or at the end of the domain part
        int dotIndex = domainPart.indexOf(".");
        if (dotIndex == -1 || dotIndex == 0 || dotIndex == domainPart.length() - 1) {
            return "Email is not valid";
        }
        // if domainString or dotString is empty, return false
        String domainString = domainPart.substring(0, dotIndex);
        String dotString = domainPart.substring(dotIndex + 1);
        if (domainString.isEmpty() || dotString.isEmpty()) {
            return "Email is not valid";
        }
        return "";
    }

    @Transactional
    public CustomerAccount createCustomerAccount(String username, String email, String password) throws Exception {
        if (username == null) {
            throw new Exception("Username must not be null");
        }
        if (username.trim().length() == 0) {
            throw new Exception("Username must not be empty");
        }
        if (accountService.checkUsernameAvailability(username)) {
            throw new Exception("Username is already taken");
        }
        if (!AccountService.isValidPassword(password).isEmpty()) {
            throw new Exception(AccountService.isValidPassword(password));
        }
        String salt = AccountService.generateSalt(8);
        String hashedPassword = AccountService.hashPassword(password, salt);
        CustomerAccount customerAccount = new CustomerAccount(username, hashedPassword, salt, email);
        customerAccountRepository.save(customerAccount);

        return customerAccount;
    }

    @Transactional
    public CustomerAccount authenticate(String email, String password) throws Exception {
        // Check input
        if (email == null || email.trim().length() == 0 || password == null || password.trim().length() == 0
                || !isValidEmail(email).isEmpty()) {
            throw new Exception("Please enter a valid email and password");
        }
        CustomerAccount customerAccount = customerAccountRepository.findByEmailAddress(email);
        if (customerAccount == null) {
            throw new Exception("There is no account associated with that email address");
        }

        String salt = customerAccount.getRandomPassword();
        String hashedPassword = AccountService.hashPassword(password, salt);
        if (hashedPassword.equals(customerAccount.getPasswordHash())) {
            return customerAccount;
        } else {
            throw new Exception("Password and username do not match");
        }
    }

    private String isValidName(String name) {
        if (name.trim().length() == 0) {
            return "Name cannot be empty";
        }
        if (AccountService.containsNumber(name)) {
            return "Name cannot contain digit";
        }
        if (containsSpecialCharacterExceptDashAndApostrophe((name))) {
            return "Name cannot contain special character other than a dash or an apostrophe";
        }
        return "";
    }

    private boolean containsSpecialCharacterExceptDashAndApostrophe(String name) {
        char dash = 145;
        char apostrophe = 146;

        for (int i = 0; i < name.length(); i++) {
            char character = name.charAt(i);
            if (!Character.isLetterOrDigit(character) && (character == dash || character == apostrophe)) {
                return true;
            }
        }
        return false;

    }

    public CustomerAccount updateAccount(int id, String username, String email, String password, String name)
            throws Exception {
        // Check for duplicate emails
        CustomerAccount dupeEmailAccount = customerAccountRepository.findByEmailAddress(email);
        if (dupeEmailAccount != null && !dupeEmailAccount.getUsername().equals(username)) {
            throw new Exception("An account with this email already exists");
        }

        // Attempt to update account
        CustomerAccount customerAccount = customerAccountRepository.findById(id);
        if (customerAccount == null) {
            throw new Exception("No account with this id exists");
        }
        String emailValidation = isValidEmail(email);
        if (!emailValidation.isEmpty()) {
            throw new Exception(emailValidation);
        }
        String passwordValidation = AccountService.isValidPassword(password);
        if (!passwordValidation.isEmpty()) {
            throw new Exception(passwordValidation);
        }
        if (name != null && !isValidName(name).isEmpty()) {
            throw new Exception(isValidName(name));
        }
        customerAccount.setEmail(email);
        String salt = AccountService.generateSalt(8);
        String hashedPassword = AccountService.hashPassword(password, salt);
        customerAccount.setPasswordHash(hashedPassword);
        customerAccount.setRandomPassword(salt);
        customerAccount.setName(name);
        customerAccountRepository.save(customerAccount);
        return customerAccount;
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}
