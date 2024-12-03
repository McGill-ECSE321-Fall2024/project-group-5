package ca.mcgill.ecse321.gamestore.dto;

import java.util.List;

public class CustomerAccountListDto {
    private List<CustomerAccountResponseDto> customers;

    public CustomerAccountListDto() {
    }

    public CustomerAccountListDto(List<CustomerAccountResponseDto> customers) {
        this.customers = customers;
    }

    // Getter
    public List<CustomerAccountResponseDto> getCustomers() {
        return customers;
    }

    // Setter
    public void setCustomers(List<CustomerAccountResponseDto> customers) {
        this.customers = customers;
    }

}
