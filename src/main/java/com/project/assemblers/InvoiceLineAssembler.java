package com.project.assemblers;

import com.project.models.InvoiceLine;

import java.util.List;

public class InvoiceLineAssembler {
    public static InvoiceLine assembleInvoiceLine(List<String> parsedLine) {
        return InvoiceLine.builder()
                .buyer(parsedLine.get(0))
                .imageName(parsedLine.get(1))
                .invoiceImage(parsedLine.get(2))
                .invoiceDueDate(parsedLine.get(3))
                .invoiceNumber(parsedLine.get(4))
                .invoiceAmount(parsedLine.get(5))
                .invoiceCurrency(parsedLine.get(6))
                .invoiceStatus(parsedLine.get(7))
                .supplier(parsedLine.get(8))
                .build();
    }
}
