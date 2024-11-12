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

    @Transactional
    public PaymentInformation createPaymentInformation(PaymentInformationRequestDto requestDTO) {
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

        // Adjusting the ID type to `int` to match the CustomerAccountRepository method
        CustomerAccount customerAccount = customerAccountRepository.findById(requestDTO.getCustomerAccountId());
        if (customerAccount == null) {
            throw new IllegalArgumentException("Customer account with the provided ID does not exist.");
        }

        PaymentInformation paymentInformation = new PaymentInformation(
                requestDTO.getCardholderName(),
                requestDTO.getCardNumber(),
                Date.valueOf(requestDTO.getExpirationDate()),
                requestDTO.getCvc(),
                requestDTO.getCardType(),
                customerAccount
        );

        return paymentInformationRepository.save(paymentInformation);
    }

    @Transactional
    public PaymentInformation getPaymentInformationById(int id) {
        return paymentInformationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No PaymentInformation with the specified ID exists."));
    }

    @Transactional
    public Iterable<PaymentInformation> getPaymentInformationsByCustomerAccountId(int customerAccountId) {
        CustomerAccount customerAccount = customerAccountRepository.findById(customerAccountId);
        if (customerAccount == null) {
            throw new IllegalArgumentException("Customer account with the specified ID does not exist.");
        }

        Iterable<PaymentInformation> paymentInformations = paymentInformationRepository.findByCustomerAccount_Id(customerAccountId);
        if (!paymentInformations.iterator().hasNext()) {
            throw new IllegalArgumentException("No PaymentInformation entries associated with this Customer Account ID.");
        }
        return paymentInformations;
    }

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
            CustomerAccount customerAccount = customerAccountRepository.findById(requestDTO.getCustomerAccountId());
            if (customerAccount != null) {
                paymentInformation.setCustomerAccount(customerAccount);
            }
        }

        return paymentInformationRepository.save(paymentInformation);
    }

    @Transactional
    public void deletePaymentInformation(int id) {
        PaymentInformation paymentInformation = paymentInformationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No PaymentInformation with the specified ID exists."));

        paymentInformationRepository.delete(paymentInformation);
    }
}
