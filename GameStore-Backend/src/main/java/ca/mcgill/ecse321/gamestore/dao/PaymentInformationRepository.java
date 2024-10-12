package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.PaymentInformation;
import org.springframework.data.repository.CrudRepository;

public interface PaymentInformationRepository extends CrudRepository<PaymentInformation, Integer> {
    

    //find PaymentInformation by cardholder name
    Iterable<PaymentInformation> findByCardholderName(String cardholderName);

    //find  PaymentInformation by card type (Visa, Mastercard, etc.)
    Iterable<PaymentInformation> findByCardType(PaymentInformation.CardType cardType);

    //find PaymentInformation by customer account ID
    Iterable<PaymentInformation> findByCustomerAccount_CustomerAccountId(int customerAccountId);

}
