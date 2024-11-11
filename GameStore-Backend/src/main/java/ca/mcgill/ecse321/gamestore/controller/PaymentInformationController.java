package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.PaymentInformationRequestDto;
import ca.mcgill.ecse321.gamestore.dto.PaymentInformationResponseDto;
import ca.mcgill.ecse321.gamestore.service.PaymentInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paymentinformation")
public class PaymentInformationController {

    @Autowired
    private PaymentInformationService paymentInformationService;

    // Endpoint to create a new payment information
    @PostMapping("/create")
    public ResponseEntity<PaymentInformationResponseDto> createPaymentInformation(@RequestBody PaymentInformationRequestDto paymentInformationRequestDTO) {
        // Input validation
        if (paymentInformationRequestDTO == null || paymentInformationRequestDTO.getCardholderName() == null || 
            paymentInformationRequestDTO.getCardNumber() == 0 || paymentInformationRequestDTO.getCvc() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Call service to create the payment information
        try {
            PaymentInformationResponseDto response = paymentInformationService.createPaymentInformation(paymentInformationRequestDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle any potential exceptions from service layer (e.g., invalid customer account)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to get payment information by ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentInformationResponseDto> getPaymentInformation(@PathVariable int id) {
        PaymentInformationResponseDto response = paymentInformationService.getPaymentInformationById(id);

        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
