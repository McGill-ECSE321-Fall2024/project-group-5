package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.gamestore.dao.PaymentInformationRepository;
import ca.mcgill.ecse321.gamestore.dto.PaymentInformationRequestDto;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.sql.Date;

@Service
public class PaymentInformationService {
    
    @Autowired
    private PaymentInformationRepository paymentInformationRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    /**
     * Creates a new PaymentInformation entry.
     * 
     * @param requestDTO the PaymentInformationRequestDto containing details to create a PaymentInformation
     * @return the newly created PaymentInformation object
     * @throws IllegalArgumentException if any required field is null or invalid, or if the customer account does not exist
     */
    @Transactional
    public PaymentInformation createPaymentInformation(PaymentInformationRequestDto requestDTO) {
        validateRequest(requestDTO);
    
        // Ensure the expiration date is in the future
        validateExpirationDate(requestDTO.getExpirationDate());
    
        // Retrieve the CustomerAccount
        CustomerAccount customerAccount = customerAccountRepository.findById(requestDTO.getCustomerAccountId()).orElseThrow(() -> new IllegalArgumentException("CustomerAccount not found with ID: " + requestDTO.getCustomerAccountId()));

    
        // Create PaymentInformation object
        PaymentInformation paymentInformation = new PaymentInformation(
                requestDTO.getCardholderName(),
                requestDTO.getCardNumber(),
                Date.valueOf(requestDTO.getExpirationDate()),
                requestDTO.getCvc(),
                requestDTO.getCardType(),
                customerAccount
        );
    
        // Save to the repository
        return paymentInformationRepository.save(paymentInformation);
    }
    
    private void validateRequest(PaymentInformationRequestDto requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("Request DTO cannot be null.");
        }
        if (requestDTO.getCardholderName() == null || requestDTO.getCardholderName().isEmpty()) {
            throw new IllegalArgumentException("Cardholder name cannot be null or empty.");
        }
        if (requestDTO.getCardNumber() <= 0) {
            throw new IllegalArgumentException("Card number must be a positive integer.");
        }
        if (requestDTO.getExpirationDate() == null) {
            throw new IllegalArgumentException("Expiration date cannot be null.");
        }
        if (requestDTO.getCvc() <= 0 || String.valueOf(requestDTO.getCvc()).length() != 3) {
            throw new IllegalArgumentException("CVC must be a 3-digit positive integer.");
        }
        if (requestDTO.getCardType() == null) {
            throw new IllegalArgumentException("Card type cannot be null.");
        }
    }
    
    private void validateExpirationDate(String expirationDate) {
        Date parsedDate = Date.valueOf(expirationDate);
        if (parsedDate.before(new Date(System.currentTimeMillis()))) {
            throw new IllegalArgumentException("Expiration date must be in the future.");
        }
    }
    

    /**
     * Retrieves PaymentInformation by its unique ID.
     * 
     * @param id the ID of the PaymentInformation to retrieve
     * @return the PaymentInformation object with the given ID
     * @throws IllegalArgumentException if no PaymentInformation is found with the specified ID
     */
    @Transactional
    public PaymentInformation getPaymentInformationById(int id) {
        return paymentInformationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No PaymentInformation with the specified ID exists."));
    }

    /**
     * Retrieves all PaymentInformation entries associated with a specific customer account ID.
     * 
     * @param customerAccountId the ID of the customer account
     * @return an iterable collection of PaymentInformation objects associated with the given customer account ID
     * @throws IllegalArgumentException if no customer account is found with the given ID, or if no PaymentInformation entries exist for the account
     */
    @Transactional
    public Iterable<PaymentInformation> getPaymentInformationsByCustomerAccountId(int customerAccountId) {
        CustomerAccount customerAccount = customerAccountRepository.findById(customerAccountId).orElseThrow(() -> new IllegalArgumentException("CustomerAccount not found with ID: " + customerAccountId));

        if (customerAccount == null) {
            throw new IllegalArgumentException("Customer account with the specified ID does not exist.");
        }

        Iterable<PaymentInformation> paymentInformations = paymentInformationRepository.findByCustomerAccount_Id(customerAccountId);
        if (!paymentInformations.iterator().hasNext()) {
            throw new IllegalArgumentException("No PaymentInformation entries associated with this Customer Account ID.");
        }
        return paymentInformations;
    }

    /**
     * Updates an existing PaymentInformation entry.
     * 
     * @param id the ID of the PaymentInformation to update
     * @param requestDTO the PaymentInformationRequestDto containing updated details for the PaymentInformation
     * @return the updated PaymentInformation object
     * @throws IllegalArgumentException if no PaymentInformation is found with the specified ID
     */
    @Transactional
    public PaymentInformation updatePaymentInformation(int id, PaymentInformationRequestDto requestDTO) {
        PaymentInformation paymentInformation = paymentInformationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No PaymentInformation with the specified ID exists."));

        if (requestDTO.getCardholderName() != null && !requestDTO.getCardholderName().isEmpty()) {
            paymentInformation.setCardholderName(requestDTO.getCardholderName());
        }
        if (requestDTO.getCardNumber() > 0) {
            paymentInformation.setCardNumber(requestDTO.getCardNumber());
        }
        if (requestDTO.getExpirationDate() != null) {
            paymentInformation.setExpirationDate(Date.valueOf(requestDTO.getExpirationDate()));
        }
        if (requestDTO.getCvc() > 0 && String.valueOf(requestDTO.getCvc()).length() == 3) {
            paymentInformation.setCvc(requestDTO.getCvc());
        }
        if (requestDTO.getCardType() != null) {
            paymentInformation.setCardType(requestDTO.getCardType());
        }
        if (requestDTO.getCustomerAccountId() > 0) {
            CustomerAccount customerAccount = customerAccountRepository.findById(requestDTO.getCustomerAccountId()).orElseThrow(() -> new IllegalArgumentException("CustomerAccount not found with ID: " + requestDTO.getCustomerAccountId()));

            if (customerAccount != null) {
                paymentInformation.setCustomerAccount(customerAccount);
            }
        }

        return paymentInformationRepository.save(paymentInformation);
    }

    /**
     * Deletes a PaymentInformation entry by its ID.
     * 
     * @param id the ID of the PaymentInformation to delete
     * @throws IllegalArgumentException if no PaymentInformation is found with the specified ID
     */
    @Transactional
    public void deletePaymentInformation(int id) {
        PaymentInformation paymentInformation = paymentInformationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No PaymentInformation with the specified ID exists."));

        paymentInformationRepository.delete(paymentInformation);
        
    }
}
