package ca.mcgill.ecse321.gamestore.dto;

public class TransactionRequestDto {
    private CustomerAccountResponseDto customerAccount;
    private AddressResponseDto address;
    private PaymentInformationResponseDto paymentInformation;
    private double totalPrice;
    private boolean isPaid;
    private boolean deliveryStatus;
    private boolean userAgreementCheck;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private TransactionRequestDto() {
    }

    public TransactionRequestDto(double aTotalPrice, boolean aIsPaid, boolean aDeliveryStatus,
            boolean aUserAgreementCheck, PaymentInformationResponseDto aPaymentInformation,
            CustomerAccountResponseDto aCustomerAccount,
            AddressResponseDto aAddress) {
        totalPrice = aTotalPrice;
        isPaid = aIsPaid;
        deliveryStatus = aDeliveryStatus;
        userAgreementCheck = aUserAgreementCheck;
        setPaymentInformation(aPaymentInformation);
        setCustomerAccount(aCustomerAccount);
        setAddress(aAddress);
    }

    public boolean setTotalPrice(double aTotalPrice) {
        boolean wasSet = false;
        totalPrice = aTotalPrice;
        wasSet = true;
        return wasSet;
    }

    public boolean setIsPaid(boolean aIsPaid) {
        boolean wasSet = false;
        isPaid = aIsPaid;
        wasSet = true;
        return wasSet;
    }

    public boolean setDeliveryStatus(boolean aDeliveryStatus) {
        boolean wasSet = false;
        deliveryStatus = aDeliveryStatus;
        wasSet = true;
        return wasSet;
    }

    public boolean setUserAgreementCheck(boolean aUserAgreementCheck) {
        boolean wasSet = false;
        userAgreementCheck = aUserAgreementCheck;
        wasSet = true;
        return wasSet;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public boolean getDeliveryStatus() {
        return deliveryStatus;
    }

    public boolean getUserAgreementCheck() {
        return userAgreementCheck;
    }

    /* Code from template association_GetOne */
    public PaymentInformationResponseDto getPaymentInformation() {
        return paymentInformation;
    }

    /* Code from template association_GetOne */
    public CustomerAccountResponseDto getCustomerAccount() {
        return customerAccount;
    }

    /* Code from template association_GetOne */
    public AddressResponseDto getAddress() {
        return address;
    }

    /* Code from template association_SetOneToMany */
    public boolean setPaymentInformation(PaymentInformationResponseDto aPaymentInformation) {
        boolean wasSet = false;
        if (aPaymentInformation == null) {
            return wasSet;
        }

        paymentInformation = aPaymentInformation;
        wasSet = true;
        return wasSet;
    }

    /* Code from template association_SetOneToMany */
    public boolean setCustomerAccount(CustomerAccountResponseDto aCustomerAccount) {
        boolean wasSet = false;
        if (aCustomerAccount == null) {
            return wasSet;
        }
        customerAccount = aCustomerAccount;
        wasSet = true;
        return wasSet;
    }

    /* Code from template association_SetOneToMany */
    public boolean setAddress(AddressResponseDto aAddress) {
        boolean wasSet = false;
        if (aAddress == null) {
            return wasSet;
        }

        address = aAddress;
        wasSet = true;
        return wasSet;
    }
}