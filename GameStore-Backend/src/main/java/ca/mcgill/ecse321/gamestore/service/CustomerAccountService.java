package ca.mcgill.ecse321.gamestore.service;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import jakarta.transaction.Transactional;

@Service
public class CustomerAccountService {
    @Autowired
    private CustomerAccountRepository customerRepository;

    public static boolean checkValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        int atIndex = email.indexOf('@');
        if (atIndex == -1 || atIndex == 0 || atIndex == email.length() - 1) {
            // No "@" symbol, or "@" is at the start or end of the string
            return false;
        }
        String localPart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex + 1);

        // if localPart or domainPart is empty return false
        if (localPart.isEmpty() || domainPart.isEmpty() || domainPart.contains("..")) {
            return false;
        }

        int dotIndex = domainPart.indexOf(".");
        if (dotIndex == -1 || dotIndex == 0 || dotIndex == domainPart.length() - 1) {
            // No "@" symbol, or "@" is at the start or end of the string
            return false;
        }
        // if domainString or dotString is empty return false
        String domainString = domainPart.substring(0, dotIndex);
        String dotString = domainPart.substring(dotIndex + 1);
        if (domainString.isEmpty() || dotString.isEmpty()) {
            return false;
        }
        return true;
    }
}