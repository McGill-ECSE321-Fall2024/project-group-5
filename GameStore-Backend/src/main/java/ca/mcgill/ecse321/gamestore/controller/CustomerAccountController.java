package ca.mcgill.ecse321.gamestore.controller;

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

import ca.mcgill.ecse321.gamestore.controller.utilities.AuthenticationUtility;
import ca.mcgill.ecse321.gamestore.dto.CustomerAccountRequestDto;
import ca.mcgill.ecse321.gamestore.dto.CustomerAccountResponseDto;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.service.CustomerAccountService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/customerAccount")
public class CustomerAccountController {

    @Autowired
    private CustomerAccountService customerAccountService;

    /**
     * GET endpoint to retrieve a CustomerAccount by its ID.
     * 
     * @param id the ID of the CustomerAccount to retrieve
     * @return CustomerAccountResponseDto representation of the CustomerAccount
     * @vivianeltain
     */
    @GetMapping("/getWithId/{id}")
    public ResponseEntity<CustomerAccountResponseDto> getCustomerAccountById(@PathVariable int id) {
        CustomerAccount customerAccount;
        try {
            customerAccount = customerAccountService.getCustomerAccountByID(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(new CustomerAccountResponseDto(customerAccount));
    }

    /**
     * POST endpoint to create a new CustomerAccount.
     * 
     * @param customerAccountRequestDto the CustomerAccountRequestDto containing
     *                                  CustomerAccount details
     * @return CustomerAccountResponseDto representation of the newly created
     *         CustomerAccount
     * @vivianeltain
     */
    @PostMapping("/create")
    public ResponseEntity<CustomerAccountResponseDto> createCustomerAccount(
            @RequestBody CustomerAccountRequestDto customerAccountRequestDto) {
        // Assuming service method handles validation for uniqueness and other checks
        CustomerAccount createdCustomerAccount;
        try {
            createdCustomerAccount = customerAccountService.createCustomerAccount(
                    customerAccountRequestDto.getUsername(), customerAccountRequestDto.getEmailAddress(),
                    customerAccountRequestDto.getPassword());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomerAccountResponseDto(createdCustomerAccount));
    }

    /**
     * PUT endpoint to update an existing CustomerAccount.
     * 
     * @param id                        the ID of the CustomerAccount to update
     * @param customerAccountRequestDto the CustomerAccountRequestDto containing
     *                                  updated details
     * @return CustomerAccountResponseDto representing the updated CustomerAccount
     * @vivianeltain
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerAccountResponseDto> updateCustomerAccount(
            @PathVariable int id, @RequestBody CustomerAccountRequestDto customerAccountRequestDto) {
        CustomerAccount updatedCustomerAccount;
        try {
            updatedCustomerAccount = customerAccountService.updateCustomerAccount(id,
                    customerAccountRequestDto.getUsername(), customerAccountRequestDto.getPassword(),
                    customerAccountRequestDto.getName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(new CustomerAccountResponseDto(updatedCustomerAccount));
    }

    /**
     * DELETE endpoint to delete a CustomerAccount by its ID.
     * 
     * @param id the ID of the CustomerAccount to delete
     * @return a message confirming the deletion of the CustomerAccount
     * @vivianeltain
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomerAccountResponseDto> deleteCustomerAccount(@PathVariable int id) {
        try {
            CustomerAccount deletedCustomerAccount = customerAccountService.deleteCustomerAccount(id);
            return ResponseEntity.ok(new CustomerAccountResponseDto(deletedCustomerAccount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * GET endpoint to get all customer accounts in repository
     * 
     * @param request
     * @return List<CustomerAccountResponseDto> representation of all the
     *         CustomerAccount
     */
    @GetMapping("/get")
    public ResponseEntity<?> getAllCustomerAccounts(HttpServletRequest request) {
        try {
            if (!AuthenticationUtility.isStaff(request))
                return ResponseEntity.status(AuthenticationUtility.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(AuthenticationUtility.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.ok().body(customerAccountService
                .getAllCustomerAccounts().stream().filter(Objects::nonNull).map(CustomerAccountResponseDto::new)
                .collect(Collectors.toList()));
    }

    /**
     * GET endpoint to retrieve a CustomerAccount by its email.
     * 
     * @param anEmail the email of the CustomerAccount to retrieve
     * @return CustomerAccountResponseDto representation of the CustomerAccount
     * @vivianeltain
     */
    @GetMapping("/getWithEmail/{email}")
    public CustomerAccountResponseDto findCustomerAccountByEmail(@PathVariable String anEmail) throws Exception {
        CustomerAccount customerAccount = customerAccountService.getCustomerAccountByEmail(anEmail);
        return new CustomerAccountResponseDto(customerAccount);
    }

    /**
     * GET endpoint to retrieve a CustomerAccount by its username.
     * 
     * @param aUsername the username of the CustomerAccount to retrieve
     * @return CustomerAccountResponseDto representation of the CustomerAccount
     * @vivianeltain
     */
    @GetMapping("/getWithUsername/{username}")
    public CustomerAccountResponseDto findCustomerAccountByUsername(@PathVariable String aUsername) throws Exception {
        CustomerAccount customerAccount = customerAccountService.getCustomerAccountByUsername(aUsername);
        return new CustomerAccountResponseDto(customerAccount);
    }

}
