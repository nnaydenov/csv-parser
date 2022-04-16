package com.project.writers;

import com.project.models.InvoiceLine;

import java.util.List;

public interface IWriter {
    void write(List<InvoiceLine> input, String headers);
}
