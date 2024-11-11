package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.gamestore.dao.PaymentInformationRepository;
import ca.mcgill.ecse321.gamestore.dto.PaymentInformationRequestDto;
import ca.mcgill.ecse321.gamestore.dto.PaymentInformationResponseDto;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class PaymentInformationService {

    @Autowired
    private PaymentInformationRepository paymentInformationRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    // Regular expression for card number (just an example, you can improve it)
    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("^[0-9]{16}$");

    @Transactional
    public PaymentInformationResponseDto createPaymentInformation(PaymentInformationRequestDto requestDto) {
        // Retrieve customer account based on customerAccountId
        Optional<CustomerAccount> optionalCustomerAccount = customerAccountRepository.findById(requestDto.getCustomerAccountId());
        if (!optionalCustomerAccount.isPresent()) {
            throw new IllegalArgumentException("Customer account not found");
        }
        CustomerAccount customerAccount = optionalCustomerAccount.get();

        // Validate card number (basic example, you can improve it with more sophisticated logic)
        if (!CARD_NUMBER_PATTERN.matcher(String.valueOf(requestDto.getCardNumber())).matches()) {
            throw new IllegalArgumentException("Invalid card number.");
        }

        // Validate expiration date (it must not be in the past)
        if (requestDto.getExpirationDate().before(new java.util.Date())) {
            throw new IllegalArgumentException("Expiration date is in the past.");
        }

        // Validate card type
        if (requestDto.getCardType() == null) {
            throw new IllegalArgumentException("Card type cannot be null.");
        }

        // Convert card number from long to int (ensure it's within the valid range for int)
        long cardNumberLong = requestDto.getCardNumber();
        if (cardNumberLong < Integer.MIN_VALUE || cardNumberLong > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Card number is too large to fit in an integer.");
        }
        int cardNumber = (int) cardNumberLong;

        // Create new PaymentInformation object
        PaymentInformation paymentInformation = new PaymentInformation();
        paymentInformation.setCardholderName(requestDto.getCardholderName());
        paymentInformation.setCardNumber(cardNumber);  // Now safely using int
        paymentInformation.setExpirationDate(requestDto.getExpirationDate());
        paymentInformation.setCvc(requestDto.getCvc());
        paymentInformation.setCardType(requestDto.getCardType());
        paymentInformation.setCustomerAccount(customerAccount);

        // Save to repository
        paymentInformation = paymentInformationRepository.save(paymentInformation);

        // Return response DTO
        return new PaymentInformationResponseDto(paymentInformation);
    }

    public PaymentInformationResponseDto getPaymentInformationById(int id) {
        Optional<PaymentInformation> paymentInformationOpt = paymentInformationRepository.findById(id);
        if (!paymentInformationOpt.isPresent()) {
            throw new IllegalArgumentException("Payment information not found for this ID.");
        }
        PaymentInformation paymentInformation = paymentInformationOpt.get();
        return new PaymentInformationResponseDto(paymentInformation);
    }
}
