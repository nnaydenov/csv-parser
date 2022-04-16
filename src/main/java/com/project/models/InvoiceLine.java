package com.project.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvoiceLine {
    private String buyer;
    private String imageName;
    private String invoiceImage;
    private String invoiceDueDate;
    private String invoiceNumber;
    private String invoiceAmount;
    private String invoiceCurrency;
    private String invoiceStatus;
    private String supplier;

    @Override
    public String toString() {
        String delimiter = ",";
        StringBuilder sb = new StringBuilder();
        sb.append(buyer).append(delimiter)
                .append(imageName).append(delimiter)
                .append(invoiceImage).append(delimiter)
                .append(invoiceDueDate).append(delimiter)
                .append(invoiceNumber).append(delimiter)
                .append(invoiceAmount).append(delimiter)
                .append(invoiceCurrency).append(delimiter)
                .append(invoiceStatus).append(delimiter)
                .append(supplier);
        return sb.toString();
    }
}

