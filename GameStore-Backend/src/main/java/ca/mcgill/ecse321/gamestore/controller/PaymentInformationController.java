package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.PaymentInformationRequestDto;
import ca.mcgill.ecse321.gamestore.dto.PaymentInformationResponseDto;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation;
import ca.mcgill.ecse321.gamestore.service.PaymentInformationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payment-information")
public class PaymentInformationController {

    @Autowired
    private PaymentInformationService paymentInformationService;

    /**
     * GET endpoint to retrieve a PaymentInformation by its ID.
     *
     * @param id the ID of the PaymentInformation to retrieve
     * @return PaymentInformationResponseDto representation of the
     *         PaymentInformation
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<PaymentInformationResponseDto> getPaymentInformationById(@PathVariable int id) {
        PaymentInformation paymentInformation;
        try {
            paymentInformation = paymentInformationService.getPaymentInformationById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(new PaymentInformationResponseDto(paymentInformation));
    }

    /**
     * GET endpoint to retrieve a list of PaymentInformation entries by customer
     * account ID.
     *
     * @param customerAccountId the ID of the customer account
     * @return a list of PaymentInformationResponseDto objects representing the
     *         PaymentInformation entries
     */
    @GetMapping("/get-by-customer-account/{id}")
    public List<PaymentInformationResponseDto> getPaymentInformationsByCustomerAccountId(
            @PathVariable int customerAccountId) {
        List<PaymentInformation> paymentInformations = (List<PaymentInformation>) paymentInformationService
                .getPaymentInformationsByCustomerAccountId(customerAccountId);
        return paymentInformations.stream()
                .map(PaymentInformationResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * POST endpoint to create a new PaymentInformation entry.
     *
     * @param requestDto the PaymentInformationRequestDto containing
     *                   PaymentInformation details
     * @return PaymentInformationResponseDto representing the newly created
     *         PaymentInformation
     */
    @PostMapping("/create")
    public PaymentInformationResponseDto createPaymentInformation(
            @RequestBody PaymentInformationRequestDto paymentInformationRequestDto) {
        PaymentInformation createdPaymentInformation = paymentInformationService
                .createPaymentInformation(paymentInformationRequestDto);
        return new PaymentInformationResponseDto(createdPaymentInformation);
    }

    /**
     * PUT endpoint to update an existing PaymentInformation entry by its ID.
     *
     * @param id         the ID of the PaymentInformation to update
     * @param requestDto the PaymentInformationRequestDto containing updated
     *                   PaymentInformation details
     * @return PaymentInformationResponseDto representing the updated
     *         PaymentInformation
     */
    @PutMapping("/update/{id}")
    public PaymentInformationResponseDto updatePaymentInformation(@PathVariable int id,
            @RequestBody PaymentInformationRequestDto requestDto) {
        PaymentInformation updatedPaymentInformation = paymentInformationService.updatePaymentInformation(id,
                requestDto);
        return new PaymentInformationResponseDto(updatedPaymentInformation);
    }

    /**
     * DELETE endpoint to remove a PaymentInformation entry by its ID.
     *
     * @param id the ID of the PaymentInformation to delete
     * @return PaymentInformationResponseDto representation of the deleted
     *         PaymentInformation
     */
    @DeleteMapping("/delete/{id}")
    public PaymentInformationResponseDto deletePaymentInformation(@PathVariable int id) {
        PaymentInformation deletedPaymentInformation = paymentInformationService.getPaymentInformationById(id);
        paymentInformationService.deletePaymentInformation(id);
        return new PaymentInformationResponseDto(deletedPaymentInformation);
    }
}
