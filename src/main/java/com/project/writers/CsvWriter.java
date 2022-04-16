package com.project.writers;

import com.project.models.InvoiceLine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvWriter implements IWriter {

    private static final String OUTPUT_PATH = "src/main/resources/generated/";

    public void write(List<InvoiceLine> input, String headers) {

        Map<String, List<InvoiceLine>> groupedLines = input.stream()
                .collect(Collectors.groupingBy(InvoiceLine::getBuyer));

        for(String key : groupedLines.keySet()) {

            try (PrintWriter pw = new PrintWriter(OUTPUT_PATH + key + ".csv")) {
                pw.println(headers);
                groupedLines.get(key).stream()
                        .map(InvoiceLine::toString)
                        .forEach(pw::println);
            } catch (IOException e){
                throw new RuntimeException(e.getMessage());
            }
        }

    }
}
