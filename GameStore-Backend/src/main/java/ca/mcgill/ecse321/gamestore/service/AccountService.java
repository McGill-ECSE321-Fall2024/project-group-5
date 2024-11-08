package ca.mcgill.ecse321.gamestore.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.Account;
import ca.mcgill.ecse321.gamestore.dao.AccountRepository;
import jakarta.transaction.Transactional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

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

    public static String hashPassword(String passwordToHash, String salt) {
        String hashedPassword = null;
        if (passwordToHash == null || passwordToHash.length() < 8) {
            throw new IllegalArgumentException("Password too short.");
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
}