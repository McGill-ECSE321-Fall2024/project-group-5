package ca.mcgill.ecse321.gamestore.dto;

import java.util.List;

public class StaffAccountListDto {
    private List<StaffAccountResponseDto> staffAccounts;

    public StaffAccountListDto() {
    }

    public StaffAccountListDto(List<StaffAccountResponseDto> staffAccounts) {
        this.staffAccounts = staffAccounts;
    }

    public List<StaffAccountResponseDto> getStaffAccounts() {
        return staffAccounts;
    }

    public void setStaffAccounts(List<StaffAccountResponseDto> staffAccounts) {
        this.staffAccounts = staffAccounts;
    }
}
