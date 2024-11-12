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
        // Validate username
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username must not be empty");
        }

        if (staffAccountRepository.existsStaffAccountByUsername(username)) {
            throw new IllegalArgumentException("Username is already taken");
        }

        // Validate name
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty");
        }

        // Validate password
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password must not be empty");
        }

        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password does not meet security requirements");
        }

        // Generate salt and hash password for encryption
        String salt = generateSalt(8); // Generate a random 8-character salt
        String hashedPassword = hashPassword(password, salt);

        // Create and save the staff account
        StaffAccount staffAccount = new StaffAccount(username, hashedPassword, salt, name);
        return staffAccountRepository.save(staffAccount);
    }

    @Transactional
    public void deleteStaffAccount(int id) {
        if (!staffAccountRepository.existsById(id)) {
            throw new IllegalArgumentException("Staff account not found");
        }
        staffAccountRepository.deleteById(id);
    }

    // Helper methods for password handling
    private boolean isValidPassword(String password) {
        // Example validation: Password must be at least 8 characters, contain a number and a letter
        return password.length() >= 8 && password.matches(".*[A-Za-z].*") && password.matches(".*[0-9].*");
    }

    private String generateSalt(int length) {
        // Generate a random alphanumeric salt of the given length
        StringBuilder salt = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            salt.append(characters.charAt(index));
        }
        return salt.toString();
    }

    private String hashPassword(String password, String salt) {
        // Example password hashing implementation using SHA-256
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder hashedPassword = new StringBuilder();
            for (byte b : hashedBytes) {
                hashedPassword.append(String.format("%02x", b));
            }
            return hashedPassword.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
