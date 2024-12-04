package ca.mcgill.ecse321.gamestore.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gamestore.dto.CustomerAccountListDto;
import ca.mcgill.ecse321.gamestore.dto.CustomerAccountRequestDto;
import ca.mcgill.ecse321.gamestore.dto.CustomerAccountResponseDto;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.service.CustomerAccountService;

@RestController
@RequestMapping("/api/customer-account")
public class CustomerAccountController {

    @Autowired
    private CustomerAccountService customerAccountService;

    // /**
    // * GET endpoint to retrieve a CustomerAccount by its ID.
    // *
    // * @param id the ID of the CustomerAccount to retrieve
    // * @return CustomerAccountResponseDto representation of the CustomerAccount
    // * @vivianeltain
    // */
    // @GetMapping(value = { "/get-with-id/{id}", "/get-with-id/{id}/" })
    // public CustomerAccountResponseDto getCustomerAccountById(@PathVariable("id")
    // int theId) {
    // CustomerAccount customerAccount =
    // customerAccountService.getCustomerAccountByID(theId);

    // return new CustomerAccountResponseDto(customerAccount);
    // }

    /**
     * GET endpoint to retrieve a CustomerAccount by its ID.
     * 
     * @param id the ID of the CustomerAccount to retrieve
     * @return CustomerAccountResponseDto representation of the CustomerAccount
     * @vivianeltain
     */
    @GetMapping("/get-with-id/{id}")
    public ResponseEntity<?> getCustomerAccountById(@PathVariable("id") int aId) {
        CustomerAccount customerAccount;
        try {
            customerAccount = customerAccountService.getCustomerAccountByID(aId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createCustomerAccount(
            @RequestBody CustomerAccountRequestDto customerAccountRequestDto) {
        // Assuming service method handles validation for uniqueness and other checks
        CustomerAccount createdCustomerAccount;
        try {
            createdCustomerAccount = customerAccountService.createCustomerAccount(
                    customerAccountRequestDto.getUsername(), customerAccountRequestDto.getEmailAddress(),
                    customerAccountRequestDto.getPassword(), customerAccountRequestDto.getName());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
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
    @PutMapping("/update-username/{email}/{username}")
    public ResponseEntity<?> updateCustomerAccountUsername(
            @PathVariable("email") String email, @PathVariable("username") String username) {
        try {
            customerAccountService.updateCustomerAccountUsername(email, username);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        CustomerAccount updatedCustomerAccount = customerAccountService.getCustomerAccountByEmail(email);

        return ResponseEntity.ok().body(new CustomerAccountResponseDto(updatedCustomerAccount));
    }

    @PutMapping("/update-password/{email}/{oldPassword}/{newPassword}")
    public ResponseEntity<?> updateCustomerAccountPassword(
            @PathVariable("email") String email, @PathVariable("oldPassword") String oldPassword,
            @PathVariable("newPassword") String newPassword) {
        try {
            customerAccountService.updateCustomerAccountPassword(email, oldPassword, newPassword);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        CustomerAccount updatedCustomerAccount = customerAccountService.getCustomerAccountByEmail(email);

        return ResponseEntity.ok().body(new CustomerAccountResponseDto(updatedCustomerAccount));
    }

    @PutMapping("/update-name/{email}/{name}")
    public ResponseEntity<?> updateCustomerAccountName(
            @PathVariable("email") String email, @PathVariable("name") String name) {
        try {
            customerAccountService.updateCustomerAccountName(email, name);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        CustomerAccount updatedCustomerAccount = customerAccountService.getCustomerAccountByEmail(email);

        return ResponseEntity.ok().body(new CustomerAccountResponseDto(updatedCustomerAccount));
    }

    /**
     * DELETE endpoint to delete a CustomerAccount by its ID.
     * 
     * @param id the ID of the CustomerAccount to delete
     * @return a message confirming the deletion of the CustomerAccount
     * @vivianeltain
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomerAccount(@PathVariable("id") int id) {
        try {
            CustomerAccount deletedCustomerAccount = customerAccountService.deleteCustomerAccount(id);
            return ResponseEntity.ok(new CustomerAccountResponseDto(deletedCustomerAccount));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * GET endpoint to get all customer accounts in repository
     * 
     * @param request
     * @return List<CustomerAccountResponseDto> representation of all the
     *         CustomerAccount
     */
    @GetMapping("/get/all")
    public ResponseEntity<?> getAllCustomerAccounts() {
        // try {
        // if (!AuthenticationUtility.isStaff(request))
        // return ResponseEntity.status(AuthenticationUtility.FORBIDDEN).build();
        // } catch (Exception e) {
        // return
        // ResponseEntity.status(AuthenticationUtility.UNAUTHORIZED).body(e.getMessage());
        // }
        List<CustomerAccountResponseDto> customers = new ArrayList<>();
        for (CustomerAccount customer : customerAccountService.getAllCustomerAccounts()) {
            customers.add(new CustomerAccountResponseDto(customer));
        }

        return ResponseEntity.ok(new CustomerAccountListDto(customers));
    }

    /**
     * GET endpoint to retrieve a CustomerAccount by its email.
     * 
     * @param anEmail the email of the CustomerAccount to retrieve
     * @return CustomerAccountResponseDto representation of the CustomerAccount
     * @vivianeltain
     */
    @GetMapping("/get-with-email/{email}")
    public ResponseEntity<?> getCustomerAccountByEmail(@PathVariable("email") String anEmail) {
        CustomerAccount customerAccount;
        try {
            customerAccount = customerAccountService.getCustomerAccountByEmail(anEmail);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok(new CustomerAccountResponseDto(customerAccount));
    }

    /**
     * GET endpoint to retrieve a CustomerAccount by its username.
     * 
     * @param aUsername the username of the CustomerAccount to retrieve
     * @return CustomerAccountResponseDto representation of the CustomerAccount
     * @vivianeltain
     */
    @GetMapping("/get-with-username/{username}")
    public ResponseEntity<?> getCustomerAccountByUsername(@PathVariable("username") String aUsername) {
        CustomerAccount customerAccount;
        try {
            customerAccount = customerAccountService.getCustomerAccountByUsername(aUsername);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok(new CustomerAccountResponseDto(customerAccount));
    }

}