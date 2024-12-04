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

import ca.mcgill.ecse321.gamestore.dto.StaffAccountResponseDto;
import ca.mcgill.ecse321.gamestore.dto.StaffAccountRequestDto;
import ca.mcgill.ecse321.gamestore.dto.StaffAccountListDto;
import ca.mcgill.ecse321.gamestore.model.StaffAccount;
import ca.mcgill.ecse321.gamestore.service.StaffAccountService;

@RestController
@RequestMapping("/api/staff-account")
public class StaffAccountController {

    @Autowired
    private StaffAccountService staffAccountService;

    @GetMapping("/get-with-id/{id}")
    public ResponseEntity<?> getStaffAccountById(@PathVariable("id") int id) {
        StaffAccount StaffAccount;
        try {
            StaffAccount = staffAccountService.getStaffAccountById(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok(new StaffAccountResponseDto(StaffAccount));
    }

    @GetMapping("/get-with-username/{username}")
    public ResponseEntity<?> getStaffAccountByUsername(@PathVariable("username") String aUsername) {
        StaffAccount StaffAccount;
        try {
            StaffAccount = staffAccountService.getStaffAccountByUsername(aUsername);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok(new StaffAccountResponseDto(StaffAccount));
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllStaffAccounts() {
        // try {
        // if (!AuthenticationUtility.isStaff(request))
        // return ResponseEntity.status(AuthenticationUtility.FORBIDDEN).build();
        // } catch (Exception e) {
        // return
        // ResponseEntity.status(AuthenticationUtility.UNAUTHORIZED).body(e.getMessage());
        // }
        List<StaffAccountResponseDto> Staffs = new ArrayList<>();
        for (StaffAccount Staff : staffAccountService.getAllStaffAccounts()) {
            Staffs.add(new StaffAccountResponseDto(Staff));
        }

        return ResponseEntity.ok(new StaffAccountListDto(Staffs));
    }

    // @PutMapping("/{id}/updatePassword")
    // public void updatePassword(@PathVariable int id, @RequestBody String
    // newPassword) {
    // staffAccountService.updatePassword(id, newPassword);
    // }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStaffAccount(@PathVariable("id") int id) {
        try {
            StaffAccount deletedStaffAccount = staffAccountService.deleteStaffAccount(id);
            return ResponseEntity.ok(new StaffAccountResponseDto(deletedStaffAccount));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createStaffAccount(
            @RequestBody StaffAccountRequestDto StaffAccountRequestDto) {
        // Assuming service method handles validation for uniqueness and other checks
        StaffAccount createdStaffAccount;
        try {
            createdStaffAccount = staffAccountService.createStaffAccount(
                    StaffAccountRequestDto.getUsername(),
                    StaffAccountRequestDto.getPassword());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new StaffAccountResponseDto(createdStaffAccount));
    }

    @PutMapping("/update-password/{username}/{oldPassword}/{newPassword}")
    public ResponseEntity<?> updateStaffAccountPassword(
            @PathVariable("username") String username, @PathVariable("oldPassword") String oldPassword,
            @PathVariable("newPassword") String newPassword) {
        try {
            staffAccountService.updateStaffAccountPassword(username, oldPassword, newPassword);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        StaffAccount updatedStaffAccount = staffAccountService.getStaffAccountByUsername(username);

        return ResponseEntity.ok().body(new StaffAccountResponseDto(updatedStaffAccount));
    }
}
