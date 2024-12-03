package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.PaymentInformationRequestDto;
import ca.mcgill.ecse321.gamestore.dto.PaymentInformationResponseDto;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation;
import ca.mcgill.ecse321.gamestore.service.PaymentInformationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/payment-information")
public class PaymentInformationController {

    private static final Logger log = LoggerFactory.getLogger(PaymentInformationController.class);

    @Autowired
    private PaymentInformationService paymentInformationService;

    /**
     * GET endpoint to retrieve a PaymentInformation by its ID.
     *
     * @param id the ID of the PaymentInformation to retrieve
     * @return PaymentInformationResponseDto representation of the
     *         PaymentInformation or NOT_FOUND status if not found
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<PaymentInformationResponseDto> getPaymentInformationById(@PathVariable int id) {
        log.info("Fetching PaymentInformation with ID: {}", id);
        try {
            PaymentInformation paymentInformation = paymentInformationService.getPaymentInformationById(id);
            return ResponseEntity.ok(new PaymentInformationResponseDto(paymentInformation));
        } catch (IllegalArgumentException e) {
            log.error("PaymentInformation with ID: {} not found. Error: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * GET endpoint to retrieve a list of PaymentInformation entries by customer account ID.
     *
     * @param customerAccountId the ID of the customer account
     * @return a list of PaymentInformationResponseDto objects representing the
     *         PaymentInformation entries or NOT_FOUND status if no entries are found
     */
    @GetMapping("/get-by-customer-account/{customerAccountId}")
public ResponseEntity<List<PaymentInformationResponseDto>> getPaymentInformationsByCustomerAccountId(
        @PathVariable int customerAccountId) {
    log.info("Fetching PaymentInformation entries for CustomerAccount ID: {}", customerAccountId);
    try {
        Iterable<PaymentInformation> paymentInformations = paymentInformationService
                .getPaymentInformationsByCustomerAccountId(customerAccountId);
        
        List<PaymentInformation> paymentInformationList = StreamSupport.stream(paymentInformations.spliterator(), false)
                .collect(Collectors.toList());
        
        if (paymentInformationList.isEmpty()) {
            log.warn("No PaymentInformation entries found for CustomerAccount ID: {}", customerAccountId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        
        List<PaymentInformationResponseDto> responseDtos = paymentInformationList.stream()
                .map(PaymentInformationResponseDto::new)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(responseDtos);
    } catch (IllegalArgumentException e) {
        log.error("Error fetching PaymentInformation for CustomerAccount ID: {}. Error: {}", customerAccountId, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}


    /**
     * POST endpoint to create a new PaymentInformation entry.
     *
     * @param paymentInformationRequestDto the PaymentInformationRequestDto containing
     *                                     PaymentInformation details
     * @return PaymentInformationResponseDto representing the newly created
     *         PaymentInformation or BAD_REQUEST if creation fails
     */
    @PostMapping("/create")
    public ResponseEntity<PaymentInformationResponseDto> createPaymentInformation(
            @RequestBody PaymentInformationRequestDto paymentInformationRequestDto) {
        log.info("Creating new PaymentInformation for CustomerAccount ID: {}", paymentInformationRequestDto.getCustomerAccountId());
        try {
            PaymentInformation createdPaymentInformation = paymentInformationService
                    .createPaymentInformation(paymentInformationRequestDto);
            log.info("Successfully created PaymentInformation with ID: {}", createdPaymentInformation.getId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new PaymentInformationResponseDto(createdPaymentInformation));
        } catch (IllegalArgumentException e) {
            log.error("Error creating PaymentInformation. Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * PUT endpoint to update an existing PaymentInformation entry by its ID.
     *
     * @param id         the ID of the PaymentInformation to update
     * @param requestDto the PaymentInformationRequestDto containing updated
     *                   PaymentInformation details
     * @return PaymentInformationResponseDto representing the updated
     *         PaymentInformation or NOT_FOUND if update fails
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<PaymentInformationResponseDto> updatePaymentInformation(@PathVariable int id,
            @RequestBody PaymentInformationRequestDto requestDto) {
        log.info("Updating PaymentInformation with ID: {}", id);
        try {
            PaymentInformation updatedPaymentInformation = paymentInformationService.updatePaymentInformation(id,
                    requestDto);
            log.info("Successfully updated PaymentInformation with ID: {}", id);
            return ResponseEntity.ok(new PaymentInformationResponseDto(updatedPaymentInformation));
        } catch (IllegalArgumentException e) {
            log.error("Error updating PaymentInformation with ID: {}. Error: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * DELETE endpoint to remove a PaymentInformation entry by its ID.
     *
     * @param id the ID of the PaymentInformation to delete
     * @return PaymentInformationResponseDto representation of the deleted
     *         PaymentInformation or NOT_FOUND if deletion fails
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PaymentInformationResponseDto> deletePaymentInformation(@PathVariable int id) {
        log.info("Deleting PaymentInformation with ID: {}", id);
        try {
            PaymentInformation deletedPaymentInformation = paymentInformationService.getPaymentInformationById(id);
            paymentInformationService.deletePaymentInformation(id);
            log.info("Successfully deleted PaymentInformation with ID: {}", id);
            return ResponseEntity.ok(new PaymentInformationResponseDto(deletedPaymentInformation));
        } catch (IllegalArgumentException e) {
            log.error("Error deleting PaymentInformation with ID: {}. Error: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
