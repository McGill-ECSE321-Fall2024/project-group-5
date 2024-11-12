package ca.mcgill.ecse321.gamestore.controller;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gamestore.controller.utilities.AuthenticationUtility;
import ca.mcgill.ecse321.gamestore.dto.CustomerAccountRequestDto;
import ca.mcgill.ecse321.gamestore.dto.CustomerAccountResponseDto;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.service.AccountService;
import ca.mcgill.ecse321.gamestore.service.CustomerAccountService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CustomerAccountController {
    @Autowired
    private CustomerAccountService customerAccountService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomerAccount(HttpServletRequest request,
            @RequestParam String email, @RequestParam String password, @RequestParam String username,
            @RequestParam String name) {
        try {
            CustomerAccount c = customerAccountService.createCustomerAccount(username, email, password);
            if (name != null && !name.trim().isEmpty()) {
                customerAccountService.updateCustomerAccount(c.getId(), username, password, name);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomerAccount(HttpServletRequest request, @PathVariable(value = "id") int aId,
            @RequestParam String email, @RequestParam String password, @RequestParam String username,
            @RequestParam String name) {
        try {
            int customerAccountId = AuthenticationUtility.getAccountId(request);
            if (customerAccountId != aId && !AuthenticationUtility.isStaff(request))
                return ResponseEntity.status(AuthenticationUtility.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(AuthenticationUtility.UNAUTHORIZED).body(e.getMessage());
        }

        try {
            customerAccountService.updateCustomerAccount(aId, username, password, name);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomerAccountById(HttpServletRequest request,
            @PathVariable(value = "id") int aId) {
        try {
            int customerAccountId = AuthenticationUtility.getAccountId(request);
            if (customerAccountId != aId && !AuthenticationUtility.isStaff(request))
                return ResponseEntity.status(AuthenticationUtility.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(AuthenticationUtility.UNAUTHORIZED).body(e.getMessage());
        }

        try {
            customerAccountService.deleteAccount(aId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCustomerAccountById(HttpServletRequest request, @PathVariable(value = "id") int aId) {
        try {
            int customerAccountId = AuthenticationUtility.getAccountId(request);
            if (customerAccountId != aId && !AuthenticationUtility.isStaff(request))
                return ResponseEntity.status(AuthenticationUtility.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(AuthenticationUtility.UNAUTHORIZED).body(e.getMessage());
        }

        try {
            CustomerAccount customerAccount = customerAccountService.getCustomerAccountByID(aId);
            CustomerAccountResponseDto customerAccountResponseDto = new CustomerAccountResponseDto(customerAccount);
            return ResponseEntity.ok().body(customerAccountResponseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

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

    @GetMapping("/get/{email}")
    public CustomerAccountResponseDto findCustomerAccountByEmail(@PathVariable String anEmail) throws Exception {
        CustomerAccount customerAccount = customerAccountService.getCustomerAccountByEmail(anEmail);
        return new CustomerAccountResponseDto(customerAccount);
    }

    @GetMapping("/get/{username}")
    public CustomerAccountResponseDto findCustomerAccountByUsername(@PathVariable String aUsername) throws Exception {
        CustomerAccount customerAccount = customerAccountService.getCustomerAccountByUsername(aUsername);
        return new CustomerAccountResponseDto(customerAccount);
    }

}
