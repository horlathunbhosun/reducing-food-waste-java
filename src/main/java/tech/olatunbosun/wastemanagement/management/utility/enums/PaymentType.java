package tech.olatunbosun.wastemanagement.management.utility.enums;

public enum PaymentType {
    CASH("cash"), CARD("card"), TRANSFER("transfer");

    private final String paymentType;

    PaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return paymentType;
    }




}
