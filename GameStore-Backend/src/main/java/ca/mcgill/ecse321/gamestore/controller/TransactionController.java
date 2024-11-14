package ca.mcgill.ecse321.gamestore.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gamestore.dto.TransactionRequestDto;
import ca.mcgill.ecse321.gamestore.dto.TransactionResponseDto;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation;
import ca.mcgill.ecse321.gamestore.model.Transaction;
import ca.mcgill.ecse321.gamestore.model.Address;

import ca.mcgill.ecse321.gamestore.service.AddressService;
import ca.mcgill.ecse321.gamestore.service.CustomerAccountService;
import ca.mcgill.ecse321.gamestore.service.PaymentInformationService;
import ca.mcgill.ecse321.gamestore.service.TransactionService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerAccountService customerAccountService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PaymentInformationService paymentInformationService;

    /**
     * GET endpoint to retrieve a Transaction by its ID.
     * 
     * @param id the ID of the Transaction to retrieve
     * @return TransactionResponseDto representation of the Transaction
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<TransactionResponseDto> findTransactionById(@PathVariable int id) {
        Transaction transaction = transactionService.findTransactionById(id);
        if (transaction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(new TransactionResponseDto(transaction));
    }

    /**
     * GET endpoint to retrieve a list of Transactions associated with a specific
     * customer ID.
     * 
     * @param customerId the ID of the customer
     * @return a list of TransactionResponseDto objects representing the
     *         Transactions
     */
    @GetMapping("/getByCustomer/{id}")
    public ResponseEntity<List<TransactionResponseDto>> findTransactionsByCustomerId(@PathVariable int customerId) {
        List<Transaction> transactions = transactionService.getTransactionsByCustomerId(customerId);

        // code from chat GPT to convert List<Transaction> to
        // List<TransactionResponseDto>
        List<TransactionResponseDto> responseDtos = transactions.stream()
                .filter(Objects::nonNull) // Remove null transactions
                .map(TransactionResponseDto::new) // Use the constructor directly
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    /**
     * GET endpoint to retrieve a list of Transactions based on their "isPaid"
     * status.
     * 
     * @param isPaid the payment status to filter by
     * @return a list of TransactionResponseDto objects representing the
     *         Transactions
     */
    @GetMapping("/getByIsPaid/{isPaid}")
    public ResponseEntity<List<TransactionResponseDto>> findTransactionsByIsPaid(@PathVariable boolean isPaid) {
        List<Transaction> transactions = transactionService.getTransactionsByIsPaid(isPaid);

        // code from chat GPT to convert List<Transaction> to
        // List<TransactionResponseDto>
        List<TransactionResponseDto> responseDtos = transactions.stream()
                .filter(Objects::nonNull) // Remove null transactions
                .map(TransactionResponseDto::new) // Use the constructor directly
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    /**
     * GET endpoint to retrieve a list of Transactions based on their delivery
     * status.
     * 
     * @param deliveryStatus the delivery status to filter by
     * @return a list of TransactionResponseDto objects representing the
     *         Transactions
     */
    @GetMapping("/getByDeliveryStatus/{deliveryStatus}")
    public ResponseEntity<List<TransactionResponseDto>> findTransactionByDeliveryStatus(
            @PathVariable boolean deliveryStatus) {
        List<Transaction> transactions = transactionService.getTransactionsByDeliveryStatus(deliveryStatus);

        // code from chat GPT to convert List<Transaction> to
        // List<TransactionResponseDto>
        List<TransactionResponseDto> responseDtos = transactions.stream()
                .filter(Objects::nonNull) // Remove null transactions
                .map(TransactionResponseDto::new) // Use the constructor directly
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    /**
     * POST endpoint to create a new Transaction.
     * 
     * @param transaction the TransactionRequestDto containing Transaction details
     * @return TransactionResponseDto representing the newly created Transaction
     */
    @PostMapping("/create")
    public ResponseEntity<TransactionResponseDto> createTransaction(@RequestBody TransactionRequestDto transaction) {
        CustomerAccount customerAccount;
        try {
            customerAccount = customerAccountService.getCustomerAccountByID(transaction.getCustomerAccount().getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Transaction createdTransaction = transactionService.createTransaction(customerAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TransactionResponseDto(createdTransaction));
    }

    /**
     * DELETE endpoint to remove a Transaction by its ID.
     * 
     * @param id the ID of the Transaction to delete
     * @return ResponseEntity with null body and an HTTP status
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable int id) {
        try {
            transactionService.deleteTransaction(id);
            return ResponseEntity.noContent().build(); // 204 No Content if deletion is successful
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * PUT endpoint to update an existing Transaction.
     * 
     * @param transaction the TransactionResponseDto containing updated Transaction
     *                    information
     * @return TransactionResponseDto representing the updated Transaction
     */
    @PutMapping("/update")
    public ResponseEntity<TransactionResponseDto> updateTransaction(@RequestBody TransactionResponseDto transaction) {
        PaymentInformation paymentInformation = paymentInformationService
                .getPaymentInformationById(transaction.getPaymentInformation().getId());
        Address address = addressService.getAddressById(transaction.getAddress().getId());

        if (paymentInformation == null || address == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Transaction updatedTransaction = transactionService.updateTransaction(
                transaction.getTransactionId(),
                transaction.getTotalPrice(),
                transaction.getIsPaid(),
                transaction.getDeliveryStatus(),
                transaction.getUserAgreementCheck(),
                address,
                paymentInformation);

        return ResponseEntity.ok(new TransactionResponseDto(updatedTransaction));
    }
}