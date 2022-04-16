package com.project.parsers;

import com.project.assemblers.InvoiceLineAssembler;
import com.project.models.InvoiceLine;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class CsvParser {

    private final String fileName;
    private String headers;

    public List<InvoiceLine> parse() {
        List<List<String>> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))){
            headers = scanner.nextLine();
            while (scanner.hasNextLine()) {
                lines.add(readLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return lines.stream().map(InvoiceLineAssembler::assembleInvoiceLine).collect(Collectors.toList());
    }

    private LinkedList<String> readLine(String line) {
        LinkedList<String> values = new LinkedList<>();
        try (Scanner lineScanner = new Scanner(line)) {
            lineScanner.useDelimiter(",");
            while (lineScanner.hasNext()) {
                values.add(lineScanner.next().trim());
            }
        }
        return values;
    }
}
