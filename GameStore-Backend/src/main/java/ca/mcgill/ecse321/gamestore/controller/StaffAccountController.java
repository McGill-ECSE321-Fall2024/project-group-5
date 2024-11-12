package ca.mcgill.ecse321.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gamestore.dto.StaffAccountRequestDto;
import ca.mcgill.ecse321.gamestore.dto.StaffAccountResponseDto;
import ca.mcgill.ecse321.gamestore.model.StaffAccount;
import ca.mcgill.ecse321.gamestore.service.StaffAccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StaffAccountController {
    @Autowired
    private StaffAccountService staffAccountService;

    @PostMapping("/staffAccounts")
    public StaffAccountResponseDto createStaffAccount(@RequestBody StaffAccountRequestDto requestDto) {
        StaffAccount staffAccount = staffAccountService.createStaffAccount(requestDto);
        return new StaffAccountResponseDto(staffAccount);
    }

    @GetMapping("/staffAccounts/{id}")
    public StaffAccountResponseDto getStaffAccountById(@PathVariable int id) {
        StaffAccount staffAccount = staffAccountService.getStaffAccountById(id);
        return new StaffAccountResponseDto(staffAccount);
    }

    @GetMapping("/staffAccounts/username/{username}")
    public StaffAccountResponseDto getStaffAccountByUsername(@PathVariable String username) {
        StaffAccount staffAccount = staffAccountService.getStaffAccountByUsername(username);
        return new StaffAccountResponseDto(staffAccount);
    }

    @GetMapping("/staffAccounts")
    public List<StaffAccountResponseDto> getAllStaffAccounts() {
        List<StaffAccount> staffAccounts = staffAccountService.getAllStaffAccounts();
        List<StaffAccountResponseDto> responseDtos = new ArrayList<>();
        for (StaffAccount staffAccount : staffAccounts) {
            responseDtos.add(new StaffAccountResponseDto(staffAccount));
        }
        return responseDtos;
}
    @DeleteMapping("/staffAccounts/{id}")
    public void deleteStaffAccount(@PathVariable int id) {
        staffAccountService.deleteStaffAccount(id);
    }
}
