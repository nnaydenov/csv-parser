package com.project;

import com.project.models.InvoiceLine;
import com.project.parsers.CsvParser;
import com.project.writers.IWriter;
import com.project.writers.WriterFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        CsvParser parser = new CsvParser("src/main/resources/invoices.csv");
        List<InvoiceLine> records = parser.parse();
        Files.createDirectories(Path.of("src/main/resources/generated"));
        IWriter writer = WriterFactory.createWriter();
        writer.write(records, parser.getHeaders());
    }
}
