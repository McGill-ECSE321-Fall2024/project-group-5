package ca.mcgill.ecse321.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gamestore.dto.StaffAccountRequestDto;
import ca.mcgill.ecse321.gamestore.dto.StaffAccountResponseDto;
import ca.mcgill.ecse321.gamestore.model.StaffAccount;
import ca.mcgill.ecse321.gamestore.service.StaffAccountService;

@RestController
@RequestMapping("/staffAccounts")
public class StaffAccountController {

    @Autowired
    private StaffAccountService staffAccountService;

    @PostMapping
    public StaffAccountResponseDto createStaffAccount(@RequestBody StaffAccountRequestDto requestDto) {
        StaffAccount staffAccount = staffAccountService.createStaffAccount(
                requestDto.getUsername(),
                requestDto.getPassword(),
                requestDto.getName()
        );
        return new StaffAccountResponseDto(staffAccount);
    }

    @GetMapping("/{id}")
    public StaffAccountResponseDto getStaffAccountById(@PathVariable int id) {
        return new StaffAccountResponseDto(staffAccountService.getStaffAccountById(id));
    }

    @GetMapping("/username/{username}")
    public StaffAccountResponseDto getStaffAccountByUsername(@PathVariable String username) {
        return new StaffAccountResponseDto(staffAccountService.getStaffAccountByUsername(username));
    }

    @PutMapping("/{id}/updatePassword")
    public void updatePassword(@PathVariable int id, @RequestBody String newPassword) {
        staffAccountService.updatePassword(id, newPassword);
    }

    @DeleteMapping("/{id}")
    public void deleteStaffAccount(@PathVariable int id) {
        staffAccountService.deleteStaffAccount(id);
    }
}
