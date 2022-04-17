package com.project.writers;

import com.project.models.InvoiceLine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CsvWriterTest {

    @BeforeEach
    void setup() throws IOException {
        Files.createDirectories(Path.of("src/test/resources"));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of("src/test/resources/testbuyer.csv"));
    }

    @Test
    void write() throws FileNotFoundException {
        String headers = "buyer,image_name,invoice_image,invoice_due_date,invoice_number,invoice_amount,invoice_currency,invoice_status,supplier";
        InvoiceLine invoiceLine = InvoiceLine.builder()
                .buyer("testbuyer")
                .invoiceAmount("100")
                .build();
        CsvWriter csvWriter = new CsvWriter();
        csvWriter.write(List.of(invoiceLine), headers, "src/test/resources/");
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("src/test/resources/testbuyer.csv"))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        }
        Assertions.assertEquals(headers, lines.get(0));
        Assertions.assertEquals(invoiceLine.toString(), lines.get(1));
    }
}