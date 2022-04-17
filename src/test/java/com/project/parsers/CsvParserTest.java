package com.project.parsers;

import com.project.models.InvoiceLine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

class CsvParserTest {

    @BeforeEach
    void setUp() throws IOException {
        InvoiceLine invoiceLine = InvoiceLine.builder()
                .buyer("testbuyer")
                .invoiceAmount("100")
                .build();
        Files.createDirectories(Path.of("src/test/resources/"));
        try (PrintWriter pw = new PrintWriter("src/test/resources/test.csv")) {
            pw.println("buyer,image_name,invoice_image,invoice_due_date,invoice_number,invoice_amount,invoice_currency,invoice_status,supplier");
            pw.println(invoiceLine.toString());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of("src/test/resources/test.csv"));
    }

    @Test
    void parse() {
        CsvParser parser = new CsvParser("src/test/resources/test.csv");
        InvoiceLine expected = InvoiceLine.builder()
                .buyer("testbuyer")
                .invoiceAmount("100")
                .build();
        InvoiceLine actual = parser.parse().get(0);
        Assertions.assertEquals(expected.toString(), actual.toString());

    }
}