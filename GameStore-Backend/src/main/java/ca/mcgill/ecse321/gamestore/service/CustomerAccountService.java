package ca.mcgill.ecse321.gamestore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import jakarta.transaction.Transactional;

@Service
public class CustomerAccountService {
    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Autowired
    private AccountService accountService;

    /**
     * Find CustomerAccount by Id
     * 
     * @param id - CustomerAccount id
     * @return CustomerAccount
     * @throws Exception
     * @vivianeltain
     */
    public CustomerAccount getCustomerAccountByID(int id) throws Exception {
        CustomerAccount customerAccount = customerAccountRepository.findById(id);
        if (customerAccount == null) {
            throw new Exception("No account associated with this id exists");
        }
        return customerAccount;
    }

    /**
     * Find CustomerAccount by email
     * 
     * @param email - CustomerAccount email
     * @return CustomerAccount
     * @throws Exception
     * @vivianeltain
     */
    public CustomerAccount getCustomerAccountByEmail(String email) throws Exception {
        CustomerAccount customerAccount = customerAccountRepository.findByEmailAddress(email);
        if (customerAccount == null) {
            throw new Exception("No account associated with this email exists");
        }
        return customerAccount;
    }

    /**
     * Find CustomerAccount by username
     * 
     * @param username - CustomerAccount username
     * @return CustomerAccount
     * @throws Exception
     * @vivianeltain
     */
    public CustomerAccount getCustomerAccountByUsername(String username) throws Exception {
        CustomerAccount customerAccount = customerAccountRepository.findByUsername(username);
        if (customerAccount == null) {
            throw new Exception("No account associated with this username exists");
        }
        return customerAccount;
    }

    /**
     * Get all list of all CustomerAccounts
     * 
     * @return List<CustomerAccount>
     * @vivianeltain
     */
    public List<CustomerAccount> getAllCustomerAccounts() {
        return toList(customerAccountRepository.findAll());
    }

    /**
     * Delete CustomerAccount with userId
     * 
     * @param id - CustomerAccount Id
     * @return CustomerAccount
     * @throws Exception
     * @vivianeltain
     */
    public CustomerAccount deleteCustomerAccount(int id) throws Exception {
        CustomerAccount customerAccount = customerAccountRepository.findById(id);
        if (customerAccount == null) {
            throw new Exception("No account associated with this id exists");
        }
        customerAccountRepository.delete(customerAccount);
        return customerAccount;
    }

    /**
     * Helper method to determine if email is valid
     * A valid email has the following format: example@ecse.ca
     * 
     * @param email
     * @return Empty String if email is valid or the error message associated
     *         with the invalid email
     * @vivianeltain
     */
    private static String isValidEmail(String email) {
        // checks that email is not null
        if (email == null) {
            return "Email cannot be null";
        }
        // check to see that email is not empty or just a bunch of spaces
        if (email.trim().isEmpty()) {
            return "Email cannot be empty";
        }
        int atIndex = email.indexOf('@');
        // checks that there is an "@" in the email that is not at the start or
        // end of the email string
        if (atIndex == -1 || atIndex == 0 || atIndex == email.length() - 1) {
            return "Email is not valid";
        }
        // seperate email in two parts: local (before@) and domain (after@)
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
        // seperate domain part in two parts: domain (before .) and dot (after .)
        String domainString = domainPart.substring(0, dotIndex);
        String dotString = domainPart.substring(dotIndex + 1);
        // if domainString or dotString is empty, return false
        if (domainString.isEmpty() || dotString.isEmpty()) {
            return "Email is not valid";
        }
        return "";
    }

    /**
     * Create CustomerAccount with given parameters
     * 
     * @param username - CustomerAccount username
     * @param email    - CustomerAccount email
     * @param password - CustomerAccount password
     * @return Newly created CustomerAccount
     * @throws Exception
     * @vivianeltain
     */
    @Transactional
    public CustomerAccount createCustomerAccount(String username, String email, String password) throws Exception {
        if (username == null) {
            throw new Exception("Username must not be null");
        }
        if (username.trim().length() == 0) {
            throw new Exception("Username must not be empty");
        }
        if (!accountService.checkUsernameAvailability(username)) {
            throw new Exception("Username is already taken");
        }
        // Check that email follows proper format
        String emailValidation = isValidEmail(email);
        if (!emailValidation.isEmpty()) {
            throw new Exception(emailValidation);
        }
        // Check that account with the same email doesn't already exist
        if (customerAccountRepository.findByEmailAddress(email) != null) {
            throw new Exception("Account associated with given email already exists");
        }
        // check that password is valid
        if (!AccountService.isValidPassword(password).isEmpty()) {
            throw new Exception(AccountService.isValidPassword(password));
        }
        // Generate salt and hash password for data encryption
        String salt = AccountService.generateSalt(8);
        String hashedPassword = AccountService.hashPassword(password, salt);
        CustomerAccount customerAccount = new CustomerAccount(username, hashedPassword, salt, email);
        customerAccountRepository.save(customerAccount);

        return customerAccount;
    }

    /**
     * Check if given email and password are associated with the same account
     * 
     * @param email    - given email
     * @param password - given password
     * @return CustomerAccount associated with given email and password
     * @throws Exception
     * @vivianeltain
     */
    @Transactional
    public CustomerAccount authenticateWithEmail(String email, String password) throws Exception {
        // Check input
        if (email == null || email.trim().length() == 0 || password == null || password.trim().length() == 0
                || !isValidEmail(email).isEmpty()) {
            throw new Exception("Please enter a valid email and password");
        }
        CustomerAccount customerAccount = customerAccountRepository.findByEmailAddress(email);
        if (customerAccount == null) {
            throw new Exception("There is no account associated with that email address");
        }
        // get salt associated with account to rehash password and compare
        String salt = customerAccount.getRandomPassword();
        String hashedPassword = AccountService.hashPassword(password, salt);
        if (hashedPassword.equals(customerAccount.getPasswordHash())) {
            return customerAccount;
        } else {
            throw new Exception("Password and email do not match");
        }
    }

    /**
     * Helper method to determine if name has valid format
     * Name cannot contain special characters or numbers except for ' and -
     * 
     * @param name - given name
     * @return Empty String if name is valid or the error message associated
     *         with the invalid name
     * @vivianeltain
     */
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

    /**
     * Helper method to see if string contains a special character except
     * for dash and apostrophe
     * 
     * @param string - given stringg
     * @return true if string does contains a special character that is not
     *         - or '
     * @vivianeltain
     */
    private boolean containsSpecialCharacterExceptDashAndApostrophe(String string) {
        List<Character> allowedSpecialChars = new ArrayList<>();
        allowedSpecialChars.add('-'); // dash
        allowedSpecialChars.add('\''); // apostrophe
        allowedSpecialChars.add(' '); // blank space

        // Check each character in the string
        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);
            // If it's not a letter, digit, or one of the allowed special characters, return
            // true
            if (!Character.isLetterOrDigit(character) && !allowedSpecialChars.contains(character)) {
                return true;
            }
        }
        // No invalid special characters found
        return false;
    }

    /**
     * Update CustomerAccount with given parameters
     * Note: email cannot change
     * 
     * @param id       - CustomerAccount Id
     * @param username - CustomerAccount username
     * @param password - CustomerAccount password
     * @param name     - CustomerAccount name *
     * @return updated CustomerAccount
     * @throws Exception
     * @vivianeltain
     */
    public CustomerAccount updateCustomerAccount(int aId, String aUsername, String aPassword, String aName)
            throws Exception {
        // check for duplicate username
        if (!accountService.checkUsernameAvailability(aUsername)) {
            throw new Exception("An account with this username already exits");
        }
        // Attempt to update account
        CustomerAccount customerAccount = customerAccountRepository.findById(aId);
        if (customerAccount == null) {
            throw new Exception("No account associated with this id exists");
        }
        // Check that new password is valid
        String passwordValidation = AccountService.isValidPassword(aPassword);
        if (!passwordValidation.isEmpty()) {
            throw new Exception(passwordValidation);
        }
        // Check that new name is valid
        if (aName != null && !isValidName(aName).isEmpty()) {
            throw new Exception(isValidName(aName));
        }
        String salt = AccountService.generateSalt(8);
        String hashedPassword = AccountService.hashPassword(aPassword, salt);
        customerAccount.setPasswordHash(hashedPassword);
        customerAccount.setRandomPassword(salt);
        customerAccount.setUsername(aUsername);
        customerAccount.setName(aName);
        customerAccountRepository.save(customerAccount);
        return customerAccount;
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
