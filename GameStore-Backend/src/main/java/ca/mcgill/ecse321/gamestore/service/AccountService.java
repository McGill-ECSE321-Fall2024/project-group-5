package ca.mcgill.ecse321.gamestore.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.Account;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.StaffAccount;
import ca.mcgill.ecse321.gamestore.dao.AccountRepository;
import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.gamestore.dao.StaffAccountRepository;
import jakarta.transaction.Transactional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Autowired
    private StaffAccountRepository staffAccountRepository;

    /**
     * Generates a random salt of wanted length
     * 
     * @param length - wanted length of the salt
     * @return String salt
     * @vivianeltain
     */
    public static String generateSalt(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder salt = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            salt.append(characters.charAt(index));
        }
        return salt.toString();
    }

    /**
     * Helper method to determine if password is valid
     * Requirements for the password to be valid:
     * - Longer than 8 characters long
     * - Contains at least one lower case letter
     * - Contains at least one upper case letter
     * - Contains at least one special character
     * - Contains at least one number
     * 
     * @param password
     * @return Empty String if password is valid or the error message associated
     *         with the invalid password
     * @vivianeltain
     */
    public static String isValidPassword(String password) {
        if (password == null) {
            return "Password cannot be null";
        }
        if (password.trim().isEmpty()) {
            return "Password cannot be empty";
        }
        if (password.length() < 8) {
            return "Password must be at least 8 characters long";
        }
        if (!containsNumber(password)) {
            return "Password must contain at least one digit";
        }
        if (!containsSpecialCharacter(password)) {
            return "Password must contain at least one special character";
        }
        if (!containsLetter(password)) {
            return "Password must contain at least one letter";
        }
        if (!hasLowerCase(password)) {
            return "Password must have at least one lower case letter";
        }
        if (!hasUpperCase(password)) {
            return "Password must have at least one upper case letter";
        }
        if (password.trim().length() != password.length()) {
            return "Password must not contain a space";
        }
        return "";
    }

    /**
     * Helper method to check if string contains a number
     * 
     * @param string
     * @return true: string contains a number
     *         false: string does not contain a number
     * @vivianeltain
     */
    public static boolean containsNumber(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to check if string contains a special character
     * 
     * @param string
     * @return true: string contains a special character
     *         false: string does not a special character
     * @vivianeltain
     */
    private static boolean containsSpecialCharacter(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isLetterOrDigit(string.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to check if string contains a letter
     * 
     * @param string
     * @return true: string contains a letter
     *         false: string does not contain a letter
     * @vivianeltain
     */
    private static boolean containsLetter(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isLetter(string.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to check if string contains an uppercase letter
     * 
     * @param string
     * @return true: string contains an uppercase letter
     *         false: string does not contain an uppercase letter
     * @vivianeltain
     */
    private static boolean hasUpperCase(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isUpperCase(string.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to check if string contains a lowercase letter
     * 
     * @param string
     * @return true: string contains a lowercase letter
     *         false: string does not contain a lowercase letter
     * @vivianeltain
     */
    private static boolean hasLowerCase(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isLowerCase(string.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * method to hash password and salt using MD5
     * 
     * @param passwordToHash
     * @param salt
     * @return String hashedPassword
     * @vivianeltain and
     *               https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
     */
    public static String hashPassword(String passwordToHash, String salt) {
        String hashedPassword = null;
        if (!isValidPassword(passwordToHash).isEmpty()) {
            return isValidPassword(passwordToHash);
        }
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Add password bytes to digest
            md.update(salt.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest(passwordToHash.getBytes());

            // This bytes[] has bytes in decimal format;
            // Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }

            // Get complete hashed password in hex format
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }

    /**
     * Check if given username and password are associated with the same account
     * 
     * @param username - given email
     * @param password - given password
     * @return CustomerAccount associated with given username and password
     * @throws Exception
     * @vivianeltain
     */
    @Transactional
    public Account authenticateWithUsername(String username, String password) throws Exception {
        // Check input
        if (username == null || username.trim().length() == 0 || password == null || password.trim().length() == 0) {
            throw new Exception("Please enter an email and a password");
        }
        Account account = null;
        CustomerAccount customerAccount = customerAccountRepository.findByUsername(username);
        StaffAccount staffAccount = staffAccountRepository.findStaffAccountByUsername(username);

        if (customerAccount != null && staffAccount == null) {
            account = customerAccount;
        }
        if (customerAccount == null && staffAccount != null) {
            account = staffAccount;
        }
        if (account == null) {
            throw new Exception("There is no account associated with that usernanme");
        }

        String salt = account.getRandomPassword();
        String hashedPassword = hashPassword(password, salt);
        if (hashedPassword.equals(account.getPasswordHash())) {
            return account;
        } else {
            throw new Exception("Password and username do not match");
        }
    }

    /**
     * Check if username isn't already taken by another account
     * 
     * @param username - wanted username
     * @return true if username is free
     *         false if username is taken
     */
    public boolean checkUsernameAvailability(String username) {
        CustomerAccount customerAccount = customerAccountRepository.findByUsername(username);
        StaffAccount staffAccount = staffAccountRepository.findStaffAccountByUsername(username);
        if (customerAccount == null && staffAccount == null) {
            return true;
        } else {
            return false;
        }
    }

}